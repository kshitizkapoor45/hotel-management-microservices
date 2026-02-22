package com.kapoor.api.gateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return route("USER-SERVICE")
                .route(path("/api/user/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("USER-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> hotelServiceRoute() {
        return route("HOTEL-SERVICE")
                .route(path("/api/hotel/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("HOTEL-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ratingServiceRoute() {
        return route("RATING-SERVICE")
                .route(path("/api/rating/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("RATING-SERVICE"))
                .build();
    }
}