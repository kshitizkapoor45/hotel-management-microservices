package com.kapoor.ai.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapoor.ai.service.dto.GeminiResponse;
import com.kapoor.ai.service.dto.HotelReviewDto;
import com.kapoor.ai.service.model.Rating;
import com.kapoor.ai.service.repository.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {
    private final HotelReviewRepository hotelReviewRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    public HotelReviewDto generateReviewSummary(Rating rating) {
        String prompt = createPrompt(rating);
        GeminiResponse response = geminiService.generateResponse(prompt);
        String raw = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();

        log.info("Generated review summary: {}", raw);
        return parseResponse(raw);
    }

    private HotelReviewDto parseResponse(String rawJson) {
        try {
            HotelReviewDto hotelReviewDto = objectMapper.readValue(rawJson, HotelReviewDto.class);
            log.info("Parsed review summary: {}", hotelReviewDto);
            return hotelReviewDto;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response: " + rawJson, e);
        }
    }

    private String createPrompt(Rating rating) {
        return """
        You are an AI system that generates structured hotel review summaries.

        Analyze the following user rating and generate a JSON response STRICTLY in this format:

        {
          "summary": "string",
          "pros": ["string"],
          "cons": ["string"],
          "sentimentScore": number
        }

        Rules:
        - Return ONLY valid JSON (no extra text, no explanation)
        - pros and cons must be arrays of strings
        - sentimentScore must be between 0 and 1
        - summary should be concise (max 2 sentences)

        Input:
        Rating: %s
        Review: %s

        Output:
        """.formatted(
                rating.getRating(),
                rating.getFeedback()
        );
    }
}