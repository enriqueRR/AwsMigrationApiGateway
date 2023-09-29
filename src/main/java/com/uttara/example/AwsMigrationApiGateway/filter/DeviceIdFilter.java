package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.service.Gen1DeviceService;
import com.uttara.example.AwsMigrationApiGateway.service.LaunchDarklyClient;
import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
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

import java.util.Map;

import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.*;


@Component
public class DeviceIdFilter implements GlobalFilter, Ordered {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Gen1DeviceService gen1DeviceService;

    @Autowired
    LaunchDarklyClient launchDarklyClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("---DeviceIdFilter----");
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        logger.info("Ngdc Flag:{}; Aws Flag:{}", launchDarklyClient.getFlagValueNgdc(), launchDarklyClient.getFlagValueAws());
        if(launchDarklyClient.getFlagValueNgdc() || launchDarklyClient.getFlagValueAws()) {
            return chain.filter(exchange);
        }else {
            if (headers.get(TsApiGatewayConstants.DEVICE_EMAIL_ID) != null) {
                String shardCodeWithHostname = gen1DeviceService.getDeviceEmailId(headers.get(TsApiGatewayConstants.DEVICE_EMAIL_ID));
                logger.info(SHARD_CODE_HOST_NAME + ": {}", shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();
                    return chain.filter(modifiedExchange);
                } else {
                    return chain.filter(exchange);
                }
            } else if (headers.get(TsApiGatewayConstants.PRINTER_CLOUD_ID) != null) {
                String shardCodeWithHostname = gen1DeviceService.getDevices(headers.get(PRINTER_CLOUD_ID));
                logger.info(SHARD_CODE_HOST_NAME + ": {}", shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();

                    return chain.filter(modifiedExchange);
                } else {
                    return chain.filter(exchange);
                }
            } else if (headers.get(TsApiGatewayConstants.JOB_ID) != null) {
                String shardCodeWithHostname = gen1DeviceService.getHostNameByShardCode(headers.get(TsApiGatewayConstants.JOB_ID));
                logger.info(SHARD_CODE_HOST_NAME + ": {}", shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();

                    return chain.filter(modifiedExchange);
                } else {
                    return chain.filter(exchange);
                }
            } else if (headers.get(OWNER_SHIP_ID) != null) {
                String shardCodeWithHostname = gen1DeviceService.getHostNameByShardCode(headers.get(OWNER_SHIP_ID));
                logger.info(SHARD_CODE_HOST_NAME + ": {}", shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();

                    return chain.filter(modifiedExchange);
                } else {
                    return chain.filter(exchange);
                }
            } else {
                return chain.filter(exchange);
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
