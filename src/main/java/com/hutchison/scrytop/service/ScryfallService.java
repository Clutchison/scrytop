package com.hutchison.scrytop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutchison.scrytop.model.card.entity.Card;
import com.hutchison.scrytop.model.scryfall.CardIdentifier;
import com.hutchison.scrytop.model.scryfall.CollectionList;
import com.hutchison.scrytop.model.scryfall.ScryfallCardDto;
import com.hutchison.scrytop.model.scryfall.response.CollectionResponse;
import com.hutchison.scrytop.model.scryfall.response.ErrorResponse;
import com.hutchison.scrytop.model.scryfall.response.ScryfallResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return cardFromJson(sendGet(suffix));
    }

    public Optional<ScryfallCardDto> getCardByMultiverseId(String id) {
        String suffix = "/cards/" + id;
        return cardFromJson(sendGet(suffix));
    }

    public Map<String, Card> getCardsByNameList(List<String> names) {
        String suffix = "/cards/collection";
        List<CollectionList> collectionLists =
                ListUtils.partition(names, 75).stream()
                        .map(nameSubList ->
                                CollectionList.builder()
                                        .identifiers(nameSubList.stream()
                                                .map(name -> CardIdentifier.builder()
                                                        .name(name)
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build())
                        .collect(Collectors.toList());

        List<CollectionResponse> responses = collectionLists.stream()
                .map(cl -> sendPost(suffix, cl))
                .filter(resp -> resp instanceof CollectionResponse)
                .map(resp -> (CollectionResponse) resp)
                .collect(Collectors.toList());

        List<String> cardNamesNotFound = responses.stream()
                .flatMap(resp -> resp.getNotFound().stream())
                .map(CardIdentifier::getName)
                .collect(Collectors.toList());
        if (cardNamesNotFound.size() > 0)
            throw new RuntimeException("Card names not found from scryfall: " + String.join(", ", cardNamesNotFound));

        return responses.stream()
                .flatMap(resp -> resp.getCards().stream())
                .map(Card::fromScryfallDto)
                .collect(Collectors.toMap(
                        Card::getName,
                        card -> card
                ));
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

    private ScryfallResponse sendPost(String suffix, Object bodyObject) {
        sleepIfNecessary();

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(bodyObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to map object to request body.");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .setHeader("Content-Type", "application/json")
                .uri(URI.create(BASE_URL + suffix))
                .build();

        log.debug("Attempting to send POST to " + BASE_URL + suffix);
        String responseBody = send(request).body();

        try {
            ScryfallResponse response = objectMapper.readValue(responseBody, ScryfallResponse.class);
            if (response instanceof ErrorResponse)
                throw new RuntimeException("Error calling scryfall: " + response.toString());
            return response;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing collection response.");
        }
    }

    private String sendGet(String suffix) {
        sleepIfNecessary();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + suffix))
                .build();

        log.debug("Attempting to send GET to " + BASE_URL + suffix);
        return send(request).body();
    }

    private HttpResponse<String> send(HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        lastRequestSendTime = new Date();
        return response;
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
