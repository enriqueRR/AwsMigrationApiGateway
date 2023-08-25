package com.uttara.example.AwsMigrationApiGateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class AwsMigrationApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsMigrationApiGatewayApplication.class, args);

    }


}
