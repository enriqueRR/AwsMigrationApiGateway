package com.uttara.example.AwsMigrationApiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class RedirectionFilter implements GatewayFilter,Ordered  {

    private final Logger logger = LoggerFactory.getLogger(RedirectionFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        logger.info("---Inside redirection filter-------");
        String shardCode = headers.get(TsApiGatewayConstants.SHARD_CODE);
        logger.info("shardCode : "+shardCode);
        String hostname = headers.get(TsApiGatewayConstants.HOST_NAME);
        logger.info("hostname : "+hostname);
        System.out.println("shardCode : +"+shardCode+"  hostname :"+hostname);
        if(hostname.contains("amazonaws"))
        {
            logger.info("-----AWS route------");
            String forwardUrl = exchange.getRequest().getURI().toString();
            request = exchange.getRequest().mutate().
                    header(TsApiGatewayConstants.AWS_ROUTE_HEADER, "true").
                    build();
            logger.info("-----AWS route forward Url-----");
            try {
                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI(forwardUrl));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return chain.filter(exchange.mutate().request(request).build());
        }else{

            logger.info("-----NGDC route------");
            String forwardUrl = exchange.getRequest().getURI().toString();
//            String queryParam = exchange.getRequest().getQueryParams().getFirst(Constants.TOKEN);
            logger.info("-----NGDC route forward Url-----");
            try {
                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI(forwardUrl));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}