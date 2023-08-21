package com.uttara.example.AwsMigrationApiGateway.routing;

import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
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
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.expand;

/**
 * Note: We want to keep this as an example of configuring a Route with a custom filter
 * <p>
 * This corresponds with the properties configuration we have
 */
@Configuration
public class ServiceRouteConfiguration {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, RedirectionFilter redirectionFilter) {


        return builder.routes().route(r -> r.path("/**").filters(f -> {
            f.filter(redirectionFilter);
                           /*    try {
                                        f.redirect(HttpStatus.PERMANENT_REDIRECT,new URL("${redirectUri}"));
                              } catch (MalformedURLException e){
                                        throw new RuntimeException(e);
                                    }*/
            return f;
        }).uri("http://localhost:8081").id("dynamic_route")).build();

    }
}

