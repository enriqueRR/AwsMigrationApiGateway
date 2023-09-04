package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.service.Gen1DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import static com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants.*;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class RedirectionFilter implements GatewayFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Gen1DeviceService gen1DeviceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        logger.info("---Inside redirection filter-------");
        String hostname = headers.get(HOST_NAME);
        if (hostname != null && hostname.contains(AMAZON_AWS)) {
            logger.info("-----AWS route------");
            String selectedLoadBalancer = fetchLBRoute(exchange.getRequest().getURI().getPath(), hostname);

            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            logger.info("AWS route Url: {}", newUri);

            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        } else if (hostname != null && !hostname.contains(AMAZON_AWS)) {
            logger.info("-----NGDC route------");
            String selectedLoadBalancer = fetchLBRoute(exchange.getRequest().getURI().getPath(), hostname);

            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            logger.info("NGDC route Url: {}", newUri);

            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        } else if (exchange.getRequest().getURI().getPath().contains(ONRAMP + SESSION_TOKEN)) {
            //onramp /tokens/session/token
            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(SESSION_TOKEN);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        } else if (exchange.getRequest().getURI().getPath().contains(ONRAMP + SCERET_TOKEN)) {
            //onramp /tokens/session/secret
            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(SCERET_TOKEN);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }  else {
            return chain.filter(exchange);
        }
    }

    private String fetchLBRoute(String path, String hostname) {
        //onramp /jobs/printjobs/
        if (path.startsWith(ONRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(PRINT_JOB_URI);
        }
        if (path.startsWith(ONRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(PRINT_JOB_URI);
        }
        //eprintcenter /jobs/printjobs/
        if (path.startsWith(EPRINT_CENTER) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(PRINT_JOB_URI);
        }
        if (path.startsWith(EPRINT_CENTER) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(PRINT_JOB_URI);
        }
        //onramp /devices/printers/
        if (path.startsWith(ONRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DEVICE_JOB_URI);
        }
        if (path.startsWith(ONRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DEVICE_JOB_URI);
        }
        //eprintcenter /devices/printers/
        if (path.startsWith(EPRINT_CENTER) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DEVICE_JOB_URI);
        }
        if (path.startsWith(EPRINT_CENTER) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DEVICE_JOB_URI);
        }
        //onramp /jobs/scanjobs/
        if (path.startsWith(ONRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(SCAN_JOB_URI);
        }
        if (path.startsWith(ONRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(SCAN_JOB_URI);
        }
        //onramp /jobs/deliveryonlyjobs/
        if (path.startsWith(ONRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DELIVERY_ONLY_JOB_URI);
        }
        if (path.startsWith(ONRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DELIVERY_ONLY_JOB_URI);
        }
        //onramp /jobs/renderjobs/
        if (path.startsWith(ONRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(RENDER_JOB_URI);
        }
        if (path.startsWith(ONRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(RENDER_JOB_URI);
        }
/*        //onramp /tokens/session/token
        if (path.contains(ONRAMP + SESSION_TOKEN)) {
            return gen1DeviceService.findNgdcRouteUri(SESSION_TOKEN);
        }
        //onramp /tokens/session/secret
        if (path.contains(ONRAMP + SCERET_TOKEN)) {
            return gen1DeviceService.findNgdcRouteUri(SCERET_TOKEN);
        }*/
        //offramp /Printers
        if (path.startsWith(OFFRAMP) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(OFFRAMP_PRINTERS);
        }
        if (path.startsWith(OFFRAMP) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(OFFRAMP_PRINTERS);
        }
        return "no route found";

    }

    @Override
    public int getOrder() {
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
    }

}