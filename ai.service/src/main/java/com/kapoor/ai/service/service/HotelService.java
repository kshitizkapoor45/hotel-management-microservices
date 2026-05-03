package com.kapoor.ai.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kapoor.ai.service.dto.HotelSearchResponse;
import com.kapoor.ai.service.model.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
    private final VectorStore vectorStore;

    public List<HotelSearchResponse> search(String query) {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(3)
                        .build()
        );
        return docs.stream()
                .map(doc -> mapToResponse(doc))
                .toList();
    }
    private HotelSearchResponse mapToResponse(Document doc) {
        try {
            String hotelId = (String) doc.getMetadata().get("hotelId");
            String rawContent = doc.getFormattedContent();

            String jsonContent = rawContent.substring(rawContent.indexOf('{'));
            log.info("JSON content {}",jsonContent);

            Hotel hotelContent = new ObjectMapper()
                    .readValue(jsonContent, Hotel.class);

            return HotelSearchResponse.builder()
                    .hotelId(hotelId)
                    .score(doc.getScore())
                    .hotel(hotelContent)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to read JSON",e);
        }
    }
}