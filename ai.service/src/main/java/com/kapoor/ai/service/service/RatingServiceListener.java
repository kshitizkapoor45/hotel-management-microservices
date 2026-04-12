package com.kapoor.ai.service.service;

import com.kapoor.ai.service.model.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceListener {

    private final AiService aiService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void processRatingUpdate(Rating rating){
        log.info("Received rating update: {}", rating);
        log.info("AI RESPONSE {}",aiService.generateReviewSummary(rating));
    }
}