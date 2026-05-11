package com.kapoor.ai.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapoor.ai.service.dto.HotelSearchResponse;
import com.kapoor.ai.service.dto.UserSearchEvent;
import com.kapoor.ai.service.model.Hotel;
import com.kapoor.ai.service.model.UserSearchHistory;
import com.kapoor.ai.service.repository.UserSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
    private final VectorStore vectorStore;
    private final RabbitTemplate rabbitTemplate;
    private final UserSearchHistoryRepository userSearchHistoryRepository;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing.userEmbedding}")
    private String userKey;

    public List<HotelSearchResponse> search(String userId,String query) {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .filterExpression("type == 'hotel'")
                        .build()
        );
        List<HotelSearchResponse> results = docs.stream()
                .map(doc -> mapToResponse(doc))
                .filter(Objects::nonNull)
                .toList();
        if(userId != null){
            publishSearchEvent(userId, query, docs, results);
        }
        return results;
    }

    private void publishSearchEvent(String userId, String query, List<Document> docs,
                                    List<HotelSearchResponse> results) {
        try {
            List<String> hotelIds = results.stream()
                    .map(HotelSearchResponse::getHotelId)
                    .toList();

            List<String> hotelJsons = docs.stream()
                    .map(doc -> (String) doc.getMetadata().get("hotelJson"))
                    .filter(Objects::nonNull)
                    .toList();

            UserSearchEvent event = new UserSearchEvent(userId,query,hotelIds,hotelJsons);

            rabbitTemplate.convertAndSend(exchange, userKey, event);
            log.info("Search event published for userId: {}", userId);

        } catch (Exception e) {
            log.error("Failed to publish search event for userId: {}", userId, e);
        }
    }

    private HotelSearchResponse mapToResponse(Document doc) {
        try {
            String hotelId = (String) doc.getMetadata().get("hotelId");
            String hotelJson = (String) doc.getMetadata().get("hotelJson");

            if (hotelJson == null || hotelJson.isBlank()) {
                log.warn("hotelJson missing for hotelId: {}", hotelId);
                return null;
            }

            Hotel hotelContent = new ObjectMapper().readValue(hotelJson, Hotel.class);
            return HotelSearchResponse.builder()
                    .hotelId(hotelId)
                    .score(doc.getScore())
                    .hotel(hotelContent)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to read JSON", e);
        }
    }

    public List<HotelSearchResponse> recommendations(String userId) {
        List<UserSearchHistory> recentSearches = userSearchHistoryRepository
                .findTop5ByUserIdOrderBySearchedAtDesc(userId);

        if (recentSearches.isEmpty()) {
            log.info("No search history for userId: {}", userId);
            return Collections.emptyList();
        }
        // Step 2: Build interest query from recent searches only
        String interestQuery = recentSearches.stream()
                .map(h -> h.getQuery() + " " + h.getHotelContext())
                .distinct()
                .collect(Collectors.joining(". "));

        log.info("Interest query for userId {}: {}", userId, interestQuery);
        return vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(interestQuery)
                                .topK(5)
                                .filterExpression("type == 'hotel'")
                                .build()
                ).stream()
                .map(this::mapToResponse)
                .filter(Objects::nonNull)
                .toList();
    }
}