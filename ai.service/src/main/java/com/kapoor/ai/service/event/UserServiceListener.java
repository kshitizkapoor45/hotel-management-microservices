package com.kapoor.ai.service.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapoor.ai.service.dto.UserSearchEvent;
import com.kapoor.ai.service.model.UserSearchHistory;
import com.kapoor.ai.service.repository.UserSearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceListener {
    private final VectorStore vectorStore;

    private final UserSearchHistoryRepository searchHistoryRepository;
    private static final int MAX_HISTORY_PER_USER = 10;

    @RabbitListener(queues = "${app.rabbitmq.queues.user}")
    public void handleUserSearchEvent(UserSearchEvent event) {
        log.info("Received search event for userId: {}", event.userId());

        try {
            String hotelContext = buildHotelContext(event.hotelJson());

            UserSearchHistory history = UserSearchHistory.builder()
                    .userId(event.userId())
                    .query(event.query())
                    .hotelContext(hotelContext)
                    .build();

            searchHistoryRepository.save(history);
            log.info("Search history saved for userId: {}", event.userId());

            pruneOldHistory(event.userId());

        } catch (Exception e) {
            log.error("Failed to save search history for userId: {}", event.userId(), e);
        }
    }

    private String buildHotelContext(List<String> hotelJsons) {
        return hotelJsons.stream()
                .map(json -> {
                    try {
                        JsonNode node = new ObjectMapper().readTree(json);
                        return String.format("%s in %s",
                                node.path("name").asText(),
                                node.path("location").asText());
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank())
                .collect(Collectors.joining(", "));
    }

    private void pruneOldHistory(String userId) {
        List<UserSearchHistory> history = searchHistoryRepository
                .findByUserIdOrderBySearchedAtDesc(userId);

        if (history.size() > MAX_HISTORY_PER_USER) {
            List<UserSearchHistory> toDelete = history.subList(MAX_HISTORY_PER_USER,
                    history.size());
            searchHistoryRepository.deleteAll(toDelete);
            log.info("Pruned {} old search records for userId: {}", toDelete.size(), userId);
        }
    }
}