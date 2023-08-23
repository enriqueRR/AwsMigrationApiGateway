package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ShardCodeFilter implements GlobalFilter, Ordered {
    final Logger logger = LoggerFactory.getLogger(ShardCodeFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("----ShardCodeFilter executed----");
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        String shardCodeWithHostname =  headers.get(TsApiGatewayConstants.SHARD_CODE_HOST_NAME);
        if(shardCodeWithHostname!=null)
        {
           List<String> s = Stream.of(shardCodeWithHostname.split("/")).collect(Collectors.toList());
            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .headers(h -> h.set(TsApiGatewayConstants.SHARD_CODE, s.get(0)))
                    .build();
            modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .headers(h -> h.set(TsApiGatewayConstants.HOST_NAME, s.get(1)))
                    .build();
            ServerWebExchange modifiedExchange = exchange
                    .mutate()
                    .request(modifiedRequest)
                    .build();
logger.info("---------------------");
            return chain.filter(modifiedExchange);
        }
        return chain.filter(exchange);
    }

      @Override
    public int getOrder() {
        return 1;
    }
}
