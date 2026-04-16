package com.kapoor.ai.service.util;

import com.kapoor.ai.service.dto.HotelReviewDto;
import com.kapoor.ai.service.model.Hotel;
import com.kapoor.ai.service.model.HotelReview;
import com.kapoor.ai.service.model.Rating;
import com.kapoor.ai.service.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmbeddingHelper {

    private final AiService aiService;

    public void updateAggregates(HotelReview review, Rating rating) {
        int currentTotal = Optional.ofNullable(review.getTotalReviews()).orElse(0);
        double currentAvg = Optional.ofNullable(review.getAverageRating()).orElse(0.0);
        double newRating = rating.getRating().doubleValue();

        int updatedTotal = currentTotal + 1;
        double newAverage = ((currentAvg * currentTotal) + newRating) / updatedTotal;

        review.setTotalReviews(updatedTotal);
        review.setAverageRating(newAverage);
    }

    public void updateAiSummaryIfNeeded(HotelReview review, Rating rating) {
        if (review.getTotalReviews() == 1 || review.getTotalReviews() % 5 == 0) {
            HotelReviewDto dto = aiService.generateReviewSummary(rating);

            review.setSummary(dto.getSummary());
            review.setPros(dto.getPros());
            review.setCons(dto.getCons());
            review.setSentimentScore(dto.getSentimentScore());
        }
    }

    public Document buildRatingDocument(Rating rating) {
        String content = """
        Hotel Review

        Rating: %d
        Feedback: %s
    """.formatted(
                rating.getRating(),
                rating.getFeedback()
        );

        return new Document(
                content,
                Map.of(
                        "hotelId", rating.getHotelId().toString(),
                        "userId", rating.getUserId().toString(),
                        "rating", rating.getRating(),
                        "type","rating"
                )
        );
    }

    public String buildHotelContent(Hotel hotel){
        String content = """
                Hotel Name: %s
                Location: %s
                
                Amenities:
                %s
                
                Description:
                %s
                """.formatted(
                hotel.getName(),
                hotel.getLocation(),
                String.join(", ", hotel.getAmenities()),
                hotel.getAbout()
        );
        return content;
    }
}