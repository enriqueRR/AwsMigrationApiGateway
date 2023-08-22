package com.uttara.example.AwsMigrationApiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class RedirectionFilter implements GatewayFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(RedirectionFilter.class);
    @Value("${custom.ngdcLoadBalancerNames}")
    private String ngdcLoadBalancerNames;

    @Value("${custom.awsLoadBalancerNames}")
    private String awsLoadBalancerNames;

    private Mono<Void> redirectToSpecificUri(ServerWebExchange exchange,String newUri) {
        // Implement redirection logic here
        // For example:
        exchange.getResponse().setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        exchange.getResponse().getHeaders().setLocation(URI.create(newUri));
        return exchange.getResponse().setComplete();
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        logger.info("---Inside redirection filter-------");
        String shardCode = headers.get(TsApiGatewayConstants.SHARD_CODE);
        logger.info("shardCode : " + shardCode);
        String hostname = headers.get(TsApiGatewayConstants.HOST_NAME);
        logger.info("hostname : " + hostname);
        System.out.println("shardCode : +" + shardCode + "  hostname :" + hostname);
        if (hostname.contains("amazonaws")) {
            logger.info("-----AWS route------");
            String[] lbNames = awsLoadBalancerNames.split(",");
            String selectedLoadBalancer = null;
            if (exchange.getRequest().getURI().getPath().startsWith(TsApiGatewayConstants.ONRAMP)) {
                selectedLoadBalancer = lbNames[0];
            }
            // Get the original request URI
            String originalUri = exchange.getRequest().getURI().toString();


            // Append the load balancer name to the URI
            String newUri = "https://" + selectedLoadBalancer + exchange.getRequest().getURI().getPath();


            logger.info("-----AWS route forward Url-----" + newUri);
            URI uri = null;
            try {
                uri=new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .uri(uri)
                    .build();

            ServerWebExchange modifiedExchange = exchange
                    .mutate()
                    .request(modifiedRequest)
                    .build();
            modifiedExchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);

            return chain.filter(modifiedExchange);
        } else {
            logger.info("-----NGDC route------");
            String[] lbNames = ngdcLoadBalancerNames.split(",");
            String selectedLoadBalancer = null;
            if (exchange.getRequest().getURI().getPath().startsWith(TsApiGatewayConstants.ONRAMP)) {
                selectedLoadBalancer = lbNames[0];
            }
            // Get the original request URI
            String originalUri = exchange.getRequest().getURI().toString();
//            logger.info("-----originalUri-----" + originalUri);
            // Append the load balancer name to the URI
            String newUri = "https://" + selectedLoadBalancer + exchange.getRequest().getURI().getPath();


            logger.info("-----NGDC route forward Url-----" + newUri);
            URI uri = null;
            try {
                uri=new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            ServerHttpRequest modifiedRequest = exchange
                    .getRequest()
                    .mutate()
                    .uri(uri)
                    .build();

            ServerWebExchange modifiedExchange = exchange
                    .mutate()
                    .request(modifiedRequest)
                    .build();
            modifiedExchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);

            return chain.filter(modifiedExchange);
        }

    }




    @Override
    public int getOrder() {
        return 3;
    }
}