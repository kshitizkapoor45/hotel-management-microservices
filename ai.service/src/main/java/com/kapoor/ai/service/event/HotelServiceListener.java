package com.kapoor.ai.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapoor.ai.service.model.Hotel;
import com.kapoor.ai.service.util.EmbeddingHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceListener {
    private final VectorStore vectorStore;
    private final EmbeddingHelper embeddingHelper;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${app.rabbitmq.queues.hotel}")
    public void handleHotelEmbedding(Hotel hotel) {
        log.info("Received hotel creation event");

        String content = embeddingHelper.buildHotelContent(hotel);  // natural language
        String docId = hotel.getId().toString();

        String hotelJson = null;
        try {
            hotelJson = objectMapper.writeValueAsString(hotel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Document doc = new Document(
                docId,
                content,
                Map.of(
                        "hotelId", hotel.getId(),
                        "hotelJson", hotelJson,
                        "type", "hotel"
                )
        );

        vectorStore.delete(List.of(docId));
        vectorStore.add(List.of(doc));
    }
}