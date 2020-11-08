package com.hutchison.scrytop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutchison.scrytop.model.card.dto.ScryfallCardDto;
import com.hutchison.scrytop.model.card.entity.Card;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
public class ScryfallService {

    private static final String BASE_URL = "https://api.scryfall.com";
    private static Date lastRequestSendTime = null;
    private static final long MIN_WAIT_MILLIS = 100;
    private final HttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScryfallService() {
        client = HttpClient.newHttpClient();
    }


    public Optional<ScryfallCardDto> getCardByName(String name) {
        String suffix = "/cards/named?exact=" + encode(name);
        return cardFromJson(send(suffix));
    }

    private Optional<ScryfallCardDto> cardFromJson(String json) {
        ScryfallCardDto card;
        try {
            log.debug("Attempting to convert card json to dto.");
            card = objectMapper.readValue(json, ScryfallCardDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Optional.of(card);
    }

    private String encode(String name) {
        try {
            log.debug("Attempting to encode " + name + " for url.");
            return java.net.URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String send(String suffix) {
        sleepIfNecessary();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + suffix))
                .build();

        HttpResponse<String> response;
        try {
            log.debug("Attempting to send GET to " + BASE_URL + suffix);
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        lastRequestSendTime = new Date();
        return response.body();
    }

    private void sleepIfNecessary() {
        if (lastRequestSendTime == null) return;
        long millisSinceLastRequest = new Date().getTime() - lastRequestSendTime.getTime();
        if (millisSinceLastRequest < MIN_WAIT_MILLIS) sleep(MIN_WAIT_MILLIS - millisSinceLastRequest);
    }

    private void sleep(long millis) {
        try {
            log.debug("Sleeping for " + millis + " millis.");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
