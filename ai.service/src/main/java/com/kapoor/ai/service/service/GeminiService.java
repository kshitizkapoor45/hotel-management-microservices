package com.kapoor.ai.service.service;

import com.kapoor.ai.service.dto.GeminiRequest;
import com.kapoor.ai.service.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {
    private final WebClient webClient;

    @Value("${spring.gemini.api.key}")
    private String apiKey;

    @Value("${spring.gemini.api.url}")
    private String url;

    private GeminiRequest buildRequest(String prompt) {
        return new GeminiRequest(
                List.of(new GeminiRequest.Content(
                        List.of(new GeminiRequest.Part(prompt))
                ))
        );
    }

    public GeminiResponse generateResponse(String prompt) {
        return webClient.post()
                .uri(url + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(buildRequest(prompt))
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();
    }
}