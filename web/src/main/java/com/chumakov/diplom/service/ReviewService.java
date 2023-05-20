package com.chumakov.diplom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ReviewService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    private static final URI uri = URI.create("http://localhost:8000");

    public static String getPrediction(String pros, String cons, String comment)
            throws InterruptedException, IOException {
        String text = pros + " " + cons + " " + comment;

        ObjectNode json = mapper.createObjectNode();
        json.put("text", text);
        System.out.println(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers
                        .ofString(json.toString()))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                                        HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
