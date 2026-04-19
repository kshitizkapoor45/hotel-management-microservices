package com.kapoor.ai.service.event;

import com.kapoor.ai.service.model.HotelReview;
import com.kapoor.ai.service.model.Rating;
import com.kapoor.ai.service.repository.HotelReviewRepository;
import com.kapoor.ai.service.util.EmbeddingHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceListener {
    private final EmbeddingHelper embeddingHelper;
    private final HotelReviewRepository hotelReviewRepository;
    private final VectorStore vectorStore;

    @RabbitListener(queues = "${app.rabbitmq.queues.rating}")
    public void processRatingUpdate(Rating rating) {
        log.info("Received rating update");

        HotelReview hotelReview = hotelReviewRepository
                .findByHotelId(rating.getHotelId())
                .orElseGet(() -> createNewHotelReview(rating));

        embeddingHelper.updateAggregates(hotelReview, rating);
        embeddingHelper.updateAiSummaryIfNeeded(hotelReview, rating);

        hotelReviewRepository.save(hotelReview);

        saveEmbedding(rating);
    }

    public void saveEmbedding(Rating rating) {
        Document document = embeddingHelper.buildRatingDocument(rating);
        vectorStore.add(List.of(document));
    }

    private HotelReview createNewHotelReview(Rating rating) {
        return HotelReview.builder()
                .hotelId(rating.getHotelId())
                .totalReviews(0)
                .averageRating(0.0)
                .build();
    }
}