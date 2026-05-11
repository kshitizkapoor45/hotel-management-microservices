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

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queues.rating}")
    private String ratingQueue;

    @Value("${app.rabbitmq.queues.user}")
    private String userQueue;

    @Value("${app.rabbitmq.queues.hotel}")
    private String hotelQueue;

    @Value("${app.rabbitmq.routing.hotelEmbedding}")
    private String hotelKey;

    @Value("${app.rabbitmq.routing.ratingEmbedding}")
    private String ratingKey;

    @Value("${app.rabbitmq.routing.userEmbedding}")
    private String userKey;

    @Bean
    public Queue ratingsQueue() {
        return new Queue(ratingQueue, true);
    }

    @Bean
    public Queue hotelQueue() {
        return new Queue(hotelQueue, true);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(userQueue, true);
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
    public Binding userBinding(DirectExchange exchange){
        return BindingBuilder.bind(userQueue()).to(exchange).with(userKey);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
