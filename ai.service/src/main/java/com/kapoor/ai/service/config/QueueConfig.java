package com.kapoor.ai.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.queues.ratingQueue}")
    private String ratingQueue;

    @Value("${spring.rabbitmq.queues.hotelQueue}")
    private String hotelQueue;

    @Value("${spring.rabbitmq.routing.hotelEmbedding}")
    private String hotelKey;

    @Value("${spring.rabbitmq.routing.ratingEmbedding}")
    private String ratingKey;

    @Bean
    public Queue ratingsQueue() {
        return new Queue(ratingQueue, true);
    }

    @Bean
    public Queue hotelQueue() {
        return new Queue(hotelQueue, true);
    }

    @Bean
    public DirectExchange ratingsExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding ratingsBinding(DirectExchange exchange){
        return BindingBuilder.bind(ratingsQueue()).to(exchange).with(ratingKey);
    }

    @Bean
    public Binding hotelBinding(DirectExchange exchange){
        return BindingBuilder.bind(hotelQueue()).to(exchange).with(hotelKey);
    }


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
