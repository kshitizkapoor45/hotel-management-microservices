package com.kapoor.ai.service.config;

import org.springframework.ai.google.genai.text.GoogleGenAiTextEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Value("${spring.ai.google.genai.api-key}")
    private String apiKey;

//    @Bean
//    public GoogleGenAiTextEmbeddingModel embeddingModel() {
//        // This manually satisfies the requirement for an EmbeddingModel bean
//        return new GoogleGenAiTextEmbeddingModel(new GoogleGenAi(apiKey));
//    }
}
