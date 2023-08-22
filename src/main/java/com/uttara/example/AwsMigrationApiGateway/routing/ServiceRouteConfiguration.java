package com.uttara.example.AwsMigrationApiGateway.routing;

import com.uttara.example.AwsMigrationApiGateway.config.WebClientConfig;
import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * Note: We want to keep this as an example of configuring a Route with a custom filter
 * <p>
 * This corresponds with the properties configuration we have
 */
@Configuration
public class ServiceRouteConfiguration {

/*  @Autowired
    private ServerWebExchange exchange;*/


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RedirectionFilter redirectionFilter) {

        return builder
                .routes()
                .route(predicateSpec -> predicateSpec
                        .path("/**")
                        .filters(spec -> spec.filter(redirectionFilter))
                        .uri("http://localhost:8081"))
                .build();

/*
        return builder.routes().route("client-portal",
                r -> r.path("/**")
                .filters(f -> {
            f.filter(redirectionFilter);
                     *//*  try {
                                    f.redirect(HttpStatus.PERMANENT_REDIRECT,new URL(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR));
                          } catch (MalformedURLException e){
                                    throw new RuntimeException(e);
                                }*//*
            return f;
        }).uri("http://localhost:8081")).build();*/

    }
}

