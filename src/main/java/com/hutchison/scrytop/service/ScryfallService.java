package com.hutchison.scrytop.service;

import com.hutchison.scrytop.model.card.entity.Card;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

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
public class ScryfallService {

    private static final String BASE_URL = "https://api.scryfall.com";
    private static Date lastRequestSendTime = null;
    private static final long MIN_WAIT_MILLIS = 100;
    private HttpClient client;

    public ScryfallService() {
        client = HttpClient.newHttpClient();
    }


    public Optional<Card> getCardByName(String name) {
        String suffix = "/cards/named?exact=" + encode(name);
        String send = send(suffix);
        log.debug(send);
        return Optional.empty();
    }

    private String encode(String name) {
        try {
            log.log(Level.DEBUG, "Attempting to encode " + name + " for url.");
            return java.net.URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String send(String suffix) {
        long millisSinceLastRequest = new Date().getTime() - lastRequestSendTime.getTime();
        if (millisSinceLastRequest < MIN_WAIT_MILLIS) sleep(MIN_WAIT_MILLIS - millisSinceLastRequest);

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

    private void sleep(long millis) {
        try {
            log.log(Level.DEBUG, "Sleeping for " + millis + " millis.");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
