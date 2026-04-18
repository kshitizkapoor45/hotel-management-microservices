package com.kapoor.ai.service.service;

import com.kapoor.ai.service.dto.HotelSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public List<HotelSearchResponse> search(String query) {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(2)
                        .build()
        );
        return docs.stream()
                .map(doc -> mapToResponse(doc))
                .toList();
    }
    private HotelSearchResponse mapToResponse(Document doc) {

        String hotelId = (String) doc.getMetadata().get("hotelId");

        return HotelSearchResponse.builder()
                .hotelId(hotelId)
                .score(doc.getScore()) // similarity score
                .content(doc.getFormattedContent()) // raw for now (you can trim later)
                .build();
    }
}