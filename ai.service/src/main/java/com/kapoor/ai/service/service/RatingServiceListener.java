package com.kapoor.ai.service.service;

import com.kapoor.ai.service.dto.HotelReviewDto;
import com.kapoor.ai.service.model.HotelReview;
import com.kapoor.ai.service.model.Rating;
import com.kapoor.ai.service.repository.HotelReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceListener {

    private final AiService aiService;
    private final HotelReviewRepository hotelReviewRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void processRatingUpdate(Rating rating) {
        log.info("Received rating update");

        HotelReview hotelReview = hotelReviewRepository
                .findByHotelId(rating.getHotelId())
                .orElseGet(() -> createNewHotelReview(rating));

        updateAggregates(hotelReview, rating);
        updateAiSummaryIfNeeded(hotelReview, rating);

        hotelReviewRepository.save(hotelReview);
    }

    private HotelReview createNewHotelReview(Rating rating) {
        return HotelReview.builder()
                .hotelId(rating.getHotelId())
                .totalReviews(0)
                .averageRating(0.0)
                .build();
    }

    private void updateAggregates(HotelReview review, Rating rating) {
        int currentTotal = Optional.ofNullable(review.getTotalReviews()).orElse(0);
        double currentAvg = Optional.ofNullable(review.getAverageRating()).orElse(0.0);
        double newRating = rating.getRating().doubleValue();

        int updatedTotal = currentTotal + 1;
        double newAverage = ((currentAvg * currentTotal) + newRating) / updatedTotal;

        review.setTotalReviews(updatedTotal);
        review.setAverageRating(newAverage);
    }

    private void updateAiSummaryIfNeeded(HotelReview review, Rating rating) {
        if (review.getTotalReviews() == 1 || review.getTotalReviews() % 5 == 0) {
            HotelReviewDto dto = aiService.generateReviewSummary(rating);

            review.setSummary(dto.getSummary());
            review.setPros(dto.getPros());
            review.setCons(dto.getCons());
            review.setSentimentScore(dto.getSentimentScore());
        }
    }
}