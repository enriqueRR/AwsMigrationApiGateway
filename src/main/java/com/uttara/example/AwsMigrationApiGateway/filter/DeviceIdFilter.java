package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.entity.Device;
import com.uttara.example.AwsMigrationApiGateway.repository.Gen1DeviceRepository;
import com.uttara.example.AwsMigrationApiGateway.service.Gen1DeviceService;
import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class DeviceIdFilter implements GlobalFilter, Ordered {
    final Logger logger = LoggerFactory.getLogger(DeviceIdFilter.class);
    @Autowired
    private Gen1DeviceService gen1DeviceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("---DeviceIdFilter----");
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        if (headers.get(TsApiGatewayConstants.DEVICE_EMAIL_ID) != null)
        {
            String shardCodeWithHostname = gen1DeviceService.getDeviceEmailId(headers.get(TsApiGatewayConstants.DEVICE_EMAIL_ID));
            System.out.println("shardCodeWithHostname :" + shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(TsApiGatewayConstants.SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();
                    return chain.filter(modifiedExchange);
                }
                else
                {
                    return chain.filter(exchange);
                }
            }
        else if (headers.get(TsApiGatewayConstants.PRINTER_CLOUD_ID) != null)
            {

                String shardCodeWithHostname = gen1DeviceService.getDevices(headers.get(TsApiGatewayConstants.PRINTER_CLOUD_ID));
                System.out.println("shardCodeWithHostname :" + shardCodeWithHostname);
                if (shardCodeWithHostname != null) {
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(TsApiGatewayConstants.SHARD_CODE_HOST_NAME, shardCodeWithHostname))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();

                    return chain.filter(modifiedExchange);
                }
                else
                {
                    return chain.filter(exchange);
                }

            }else{

            return chain.filter(exchange);
        }

        }

        @Override
        public int getOrder () {
            return 1;
        }
    }
