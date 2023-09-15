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

import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.*;

@Component
public class PayLoadExtractFilter implements GlobalFilter, Ordered {

    final Logger logger = LoggerFactory.getLogger(PayLoadExtractFilter.class);
    private final List<HttpMessageReader<?>> messageReaders = getMessageReaders();

    private List<HttpMessageReader<?>> getMessageReaders() {
        return HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("---PayLoadExtractFilter----");
        String uriPath = exchange.getRequest().getURI().getPath();
        // Only apply on POST,PUT requests and If body contains xml
        // If body contains xml files then we are going inside and extracting the deviceId or deviceEmailId
        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        if ((HttpMethod.POST.equals(exchange.getRequest().getMethod())) && (headers.get(CONTENT_TYPE) != null)) {
            return logRequestBody(exchange, chain);
        }  else {
            ServerHttpRequest modifiedRequest = null;
            //---------------------onramp-------------------------------------/
            if (uriPath.startsWith(ONRAMP)) {
                //onramp /jobs/printjobs/
                if (exchange.getRequest().getURI().getPath().contains(PRINT_JOB_URI)) {
                    String str1 = ONRAMP + PRINT_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /jobs/scanjobs/
                if (uriPath.contains(SCAN_JOB_URI)) {
                    String str1 = ONRAMP + SCAN_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /jobs/deliveryonlyjobs/
                if (uriPath.contains(DELIVERY_ONLY_JOB_URI)) {
                    String str1 = ONRAMP + DELIVERY_ONLY_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /devices/printers/
                if (uriPath.contains(DEVICE_JOB_URI)) {
                    String str1 = ONRAMP + DEVICE_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /jobs/renderjobs/
                if (uriPath.contains(RENDER_JOB_URI)) {
                    String str1 = ONRAMP + RENDER_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //onramp /tokens/session/token
                if (uriPath.contains(SESSION_TOKEN)) {
                    return chain.filter(exchange);
                }
                //onramp /tokens/session/secret
                if (uriPath.contains(SCERET_TOKEN)) {
                    return chain.filter(exchange);
                }
                return chain.filter(exchange);
            }
            /**
             * if block will be executed for eprintcenter 'GET' request
             */
            if (uriPath.startsWith(EPRINT_CENTER)) {
                //eprintcenter /jobs/printjobs/
                if (uriPath.contains(PRINT_JOB_URI)) {
                    String str1 = EPRINT_CENTER + PRINT_JOB_URI;
                    String str2 = uriPath.substring(str1.length(), uriPath.length() - 1);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(JOB_ID, Parsing.getJobId(str2))).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //eprintcenter /devices/printers/
                if (uriPath.contains(DEVICE_JOB_URI)) {
                    String printerId = findValueFromUri(uriPath, 4);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }
                //eprintcenter owner ship uncliam printer
                if (uriPath.contains(OWNER_SHIP)) {
                    String ownershipID = findValueFromUri(uriPath, 3);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(OWNER_SHIP_ID, ownershipID)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);

                }

                return chain.filter(exchange);
            }

            /**
             * if block will be executed for offramp 'GET' request
             */
            if (uriPath.startsWith(OFFRAMP)) {
                //offramp /Printers
                if (uriPath.contains(OFFRAMP_PRINTERS)) {
                    String printerId = findValueFromUri(uriPath, 3);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                } else {
                    String deviceEmailId = findValueFromUri(uriPath, 2);
                    modifiedRequest = exchange.getRequest().mutate().headers(h -> h.set(DEVICE_EMAIL_ID, deviceEmailId)).build();
                    ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                    return chain.filter(modifiedExchange);
                }

            }
            return chain.filter(exchange);
        }
    }

    private Mono<Void> logRequestBody(ServerWebExchange exchange, GatewayFilterChain chain) {

        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
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
                    .create(exchange.mutate().request(mutatedRequest).build(), messageReaders).bodyToMono(String.class).flatMap(body -> {
                        // do what ever you want with this body string, I logged it.
                         if(body.contains(namespacePrefix)) {
                            String deviceEmailId = Parsing.getDeviceEmailId(body, DEVICE_EMAILID_XPATH);
                            logger.info("deviceEmailId: {}", deviceEmailId);
                            Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
                            if (deviceEmailId != null) {
                                exchange.getRequest().mutate().headers(h -> h.set(TsApiGatewayConstants.DEVICE_EMAIL_ID, deviceEmailId)).build();
                            } else {
                                String deviceId = Parsing.getDeviceEmailId(body, DEVICE_ID_XPATH);
                                logger.info("deviceId: {}", deviceId);
                                exchange.getRequest().mutate().headers(h -> h.set(TsApiGatewayConstants.PRINTER_CLOUD_ID, deviceId)).build();

                            }

                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        }
                        else  //eprintcenter owner ship claim printer
                        {
                            String printerEmailId = Parsing.getPrinterEmailId(body);
                            if(printerEmailId !=null) {
                                exchange.getRequest().mutate().headers(h -> h.set(TsApiGatewayConstants.DEVICE_EMAIL_ID, printerEmailId)).build();
                            }else{//blacklist //whitelist
                                String printerId = findValueFromUri(exchange.getRequest().getURI().getPath(), 4);
                                exchange.getRequest().mutate().headers(h -> h.set(PRINTER_CLOUD_ID, printerId)).build();
                            }

                            return chain.filter(exchange.mutate().request(mutatedRequest).build());
                        }
                    });
        });
    }

    private String findValueFromUri(String path, int segment) {
        String[] segments = path.split("/");
        return segments[segment];
    }

    @Override
    public int getOrder() {
        return -1;
    }
}