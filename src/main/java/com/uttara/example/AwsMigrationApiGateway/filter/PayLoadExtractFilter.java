package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.utility.Parsing;
import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class PayLoadExtractFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PayLoadExtractFilter.class);
    public static final String deviceIdXPath = "/ns1:PrintJob/ns1:PrintJobProcessingElements/ns1:DeviceId/text()";
    public static final String deviceEmailIdXPath = "/ns1:PrintJob/ns1:PrintJobProcessingElements/ns1:DeviceEmailId/text()";
    public static final String deviceIdXPathForDeliveryOnlyJob = "/ns1:DeliveryOnlyJob/ns1:DeliveryOnlyJobProcessingElements/ns1:DeviceId/text()";
    public static final String deviceEmailIdXPathForDeliveryOnlyJob = "m/ns1:DeliveryOnlyJob/ns1:DeliveryOnlyJobProcessingElements/ns1:DeviceEmailId/text()";

    private final List<HttpMessageReader<?>> messageReaders = getMessageReaders();


    private List<HttpMessageReader<?>> getMessageReaders() {
        return HandlerStrategies.withDefaults().messageReaders();
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("---PayLoadExtractFilter----");
        // Only apply on POST,PUT requests and If body contains xml
        // If body contains xml files then we are going inside and extracting the deviceId or deviceEmailId
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        if ((HttpMethod.POST.equals(exchange.getRequest().getMethod()) ||
                HttpMethod.PUT.equals(exchange.getRequest().getMethod()))
                && (headers.get("Content-Type") != null
                && headers.get("Content-Type").equals("application/xml"))) {
            return logRequestBody(exchange, chain);
        } else {
            //---------------------onramp-------------------------------------

            if (exchange.getRequest().getURI().getPath().startsWith(TsApiGatewayConstants.ONRAMP))
            {
                //onramp print jobs
                    if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.PRINT_JOB_URI))
                    {
                        String str = exchange.getRequest().getURI().getPath();
                        String str1 =TsApiGatewayConstants.ONRAMP + TsApiGatewayConstants.PRINT_JOB_URI;
                        String str2= str.substring(str1.length(),str.length()-1);

                        ServerHttpRequest modifiedRequest = exchange
                                .getRequest()
                                .mutate()
                                .headers(h -> h.set(TsApiGatewayConstants.JOB_ID, Parsing.getJobId(str2)))
                                .build();

                        ServerWebExchange modifiedExchange = exchange
                                .mutate()
                                .request(modifiedRequest)
                                .build();
                        return chain.filter(modifiedExchange);
                     }
                //onramp scan jobs
                  if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.SCAN_JOB_URI))
                    {
                        String str = exchange.getRequest().getURI().getPath();
                        String str1 =TsApiGatewayConstants.ONRAMP + TsApiGatewayConstants.SCAN_JOB_URI;
                        String str2= str.substring(str1.length(),str.length()-1);
                        ServerHttpRequest modifiedRequest = exchange
                                .getRequest()
                                .mutate()
                                .headers(h -> h.set(TsApiGatewayConstants.JOB_ID, Parsing.getJobId(str2)))
                                .build();

                        ServerWebExchange modifiedExchange = exchange
                                .mutate()
                                .request(modifiedRequest)
                                .build();
                        return chain.filter(modifiedExchange);
                    }
                //onramp deliveryonly jobs
                    if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.DELIVERY_ONLY_JOB_URI))
                    {
                        String str = exchange.getRequest().getURI().getPath();
                        String str1 =TsApiGatewayConstants.ONRAMP + TsApiGatewayConstants.DELIVERY_ONLY_JOB_URI;
                        String str2= str.substring(str1.length(),str.length()-1);
                        ServerHttpRequest modifiedRequest = exchange
                                .getRequest()
                                .mutate()
                                .headers(h -> h.set(TsApiGatewayConstants.JOB_ID, Parsing.getJobId(str2)))
                                .build();

                        ServerWebExchange modifiedExchange = exchange
                                .mutate()
                                .request(modifiedRequest)
                                .build();
                        return chain.filter(modifiedExchange);
                    }
            }
            //---------------------eprintcenter-------------------------------------
            if (exchange.getRequest().getURI().getPath().startsWith(TsApiGatewayConstants.EPRINT_CENTER))
            {
                //eprintcenter print jobs
                if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.PRINT_JOB_URI))
                {
                    String str = exchange.getRequest().getURI().getPath();
                    String str1 =TsApiGatewayConstants.EPRINT_CENTER + TsApiGatewayConstants.PRINT_JOB_URI;
                    String str2= str.substring(str1.length(),str.length()-1);
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(TsApiGatewayConstants.JOB_ID, Parsing.getJobId(str2)))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();
                    return chain.filter(modifiedExchange);
                }
                            //eprintcenter devices prints
                if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.PRINTER_URI_ONRAMP))
                {
                    String str = exchange.getRequest().getURI().getPath();
                    String str1 =TsApiGatewayConstants.EPRINT_CENTER + TsApiGatewayConstants.PRINTER_URI_ONRAMP;
                    String str2= str.substring(str1.length(),str.length()-1);
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(TsApiGatewayConstants.PRINTER_CLOUD_ID, str2))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();
                    return chain.filter(modifiedExchange);

                }
                //eprintcenter owner ship
                if (exchange.getRequest().getURI().getPath().contains(TsApiGatewayConstants.OWNER_SHIP))
                {
                    String str = exchange.getRequest().getURI().getPath();
                    String str1 =TsApiGatewayConstants.EPRINT_CENTER + TsApiGatewayConstants.OWNER_SHIP;
                    String str2= str.substring(str1.length(),str.length()-1);
                    ServerHttpRequest modifiedRequest = exchange
                            .getRequest()
                            .mutate()
                            .headers(h -> h.set(TsApiGatewayConstants.PRINTER_CLOUD_ID, str2))
                            .build();

                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(modifiedRequest)
                            .build();
                    return chain.filter(modifiedExchange);
                }
            }

            return chain.filter(exchange);
        }

    }

    private Mono<Void> logRequestBody(ServerWebExchange exchange, GatewayFilterChain chain) {

        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(dataBuffer -> {
                    DataBufferUtils.retain(dataBuffer);

                    Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));

                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            return cachedFlux;
                        }
                    };

                    return ServerRequest
                            // must construct a new exchange instance, same as below
                            .create(exchange.mutate().request(mutatedRequest).build(), messageReaders)
                            .bodyToMono(String.class)
                            .flatMap(body -> {
                                // do what ever you want with this body string, I logged it.
                                String deviceEmailId = Parsing.getDeviceEmailId(body, deviceEmailIdXPath);
                                System.out.println("deviceEmailId :" + deviceEmailId);
                                Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
                                if(deviceEmailId!=null) {
                                   exchange.getRequest()
                                            .mutate()
                                            .headers(h -> h.set(TsApiGatewayConstants.DEVICE_EMAIL_ID, deviceEmailId))
                                            .build();
                                }else {
                                    String deviceId= Parsing.getDeviceEmailId(body, deviceIdXPath);
                                    exchange.getRequest()
                                            .mutate()
                                            .headers(h -> h.set(TsApiGatewayConstants.PRINTER_CLOUD_ID, deviceId))
                                            .build();
                                }

                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            });
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}