package com.uttara.example.AwsMigrationApiGateway.routing;


import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceRouteConfiguration {

    @Autowired
    private RedirectionFilter redirectionFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .alwaysTrue()
                        .filters(f -> f.filter(redirectionFilter))
                        .uri("no://op"))
                .build();
    }
}


