package com.uttara.example.AwsMigrationApiGateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class AwsMigrationApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsMigrationApiGatewayApplication.class, args);

    }


}
