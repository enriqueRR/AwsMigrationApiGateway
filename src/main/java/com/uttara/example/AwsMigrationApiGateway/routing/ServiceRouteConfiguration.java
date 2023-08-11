package com.uttara.example.AwsMigrationApiGateway.routing;

import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter.Config;
import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Note: We want to keep this as an example of configuring a Route with a custom filter
 * <p>
 * This corresponds with the properties configuration we have
 */
@Configuration
public class ServiceRouteConfiguration {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder,RedirectionFilter redirectionFilter) {
        return builder.routes()////  on ramp routes-----------
                .route("aws_route", r -> r
                             .path("/**")
                                .filters(f->
                                        f.filter(redirectionFilter.apply(new Config(true))))
                        .uri("http://localhost:8081/*")
                )

                .route("ngdc_route", r -> r
                        .path("/**")
                        .filters(f->
                                f.filter(redirectionFilter.apply(new Config(false))))
                        .uri("http://localhost:8082/*")
                )
               .build();
    }
}
