package com.uttara.example.AwsMigrationApiGateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient client() {
        return WebClient.builder()
                .build();

    }


}