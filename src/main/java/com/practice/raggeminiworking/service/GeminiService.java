package com.practice.raggeminiworking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askGeminiWithContext(String context, String question) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                            + apiKey;

            String prompt = """
Answer the question using only the provided context.
If the answer is not in the context say Not found in document.

Context:
%s

Question:
%s
""".formatted(context, question);

            ObjectMapper mapper = new ObjectMapper();

            String body = mapper.writeValueAsString(
                    Map.of(
                            "contents",
                            List.of(
                                    Map.of(
                                            "parts",
                                            List.of(
                                                    Map.of(
                                                            "text",
                                                            prompt
                                                    )
                                            )
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(url, request, String.class);

//            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}