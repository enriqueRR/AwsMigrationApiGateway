package com.uttara.example.AwsMigrationApiGateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.server.ServerWebExchange;


@SpringBootApplication

public class AwsMigrationApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsMigrationApiGatewayApplication.class, args);

    }


}
