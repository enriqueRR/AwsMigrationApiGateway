package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.service.LaunchDarklyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.*;

@Component
public class ShardCodeFilter implements GlobalFilter, Ordered {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LaunchDarklyClient launchDarklyClient;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("----ShardCodeFilter executed----");
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        String shardCodeWithHostname = headers.get(SHARD_CODE_HOST_NAME);
        if(launchDarklyClient.getFlagValueNgdc() || launchDarklyClient.getFlagValueAws()) {
            return chain.filter(exchange);
        }
        else if (shardCodeWithHostname != null) {
            List<String> s = Stream.of(shardCodeWithHostname.split("/")).collect(Collectors.toList());
            logger.info("ShardCode: {}; HostName: {}", s.get(0), s.get(1));
            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .headers(h -> {
                        h.set(SHARD_CODE, s.get(0));
                        h.set(HOST_NAME, s.get(1));
                    }).build();
            ServerWebExchange modifiedExchange = exchange
                    .mutate()
                    .request(modifiedRequest)
                    .build();
            return chain.filter(modifiedExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
