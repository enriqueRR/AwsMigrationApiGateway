package com.uttara.example.AwsMigrationApiGateway.filter;

import com.uttara.example.AwsMigrationApiGateway.service.Gen1DeviceService;
import com.uttara.example.AwsMigrationApiGateway.service.LaunchDarklyClient;
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

    @Autowired
   private LaunchDarklyClient launchDarklyClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
        String hostname = headers.get(HOST_NAME);
        logger.info("---Inside redirection filter-------");
        if(launchDarklyClient.getFlagValueNgdc())
        {
            String selectedLoadBalancer = fetchLBRoute(exchange.getRequest().getURI().getPath(), "ngdc");
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e)
            {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);

        }
        else if(launchDarklyClient.getFlagValueAws())
        {
            String selectedLoadBalancer = fetchLBRoute(exchange.getRequest().getURI().getPath(), AMAZON_AWS);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }
        else if (hostname != null && hostname.contains(AMAZON_AWS))
        {
            logger.info("-----AWS route------");
            String selectedLoadBalancer = fetchLBRoute(exchange.getRequest().getURI().getPath(), hostname);

            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            logger.info("AWS route Url: {}", newUri);

            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e)
            {
                throw new RuntimeException(e);
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }
        else if (hostname != null && !hostname.contains(AMAZON_AWS))
        {
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
        }
/*        if (exchange.getRequest().getURI().getPath().contains(ONRAMP + SESSION_TOKEN))
        {
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
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
      /*  if (exchange.getRequest().getURI().getPath().contains(ONRAMP + SCERET_TOKEN))
        {
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
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
/*        if (exchange.getRequest().getURI().getPath().contains(ONRAMP+HEALTHCHECK))
        {//onramp/healthcheck

            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(ONRAMP+HEALTHCHECK);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
   /*     if (exchange.getRequest().getURI().getPath().contains(EPRINT_CENTER+HEALTHCHECK)) {//eprintcenter/healthcheck
             String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(EPRINT_CENTER+HEALTHCHECK);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
      /*  if (exchange.getRequest().getURI().getPath().contains(OFFRAMP+HEALTHCHECK)) {//offramp/healthcheck

            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(OFFRAMP+HEALTHCHECK);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
   /*     if (exchange.getRequest().getURI().getPath().contains(VERSION_)) {// onramp/version

            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(ONRAMP+VERSION_);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
/*        if (exchange.getRequest().getURI().getPath().contains(VERSION))
        {// eprintcenter/version

            String selectedLoadBalancer = gen1DeviceService.findNgdcRouteUri(EPRINT_CENTER+VERSION);
            // Append the load balancer name to the URI
            String newUri = HTTPS + selectedLoadBalancer + exchange.getRequest().getURI().getPath();
            URI uri = null;
            try {
                uri = new URI(newUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            logger.info("route Url: {}", newUri);
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            return chain.filter(exchange);
        }*/
        else {
            return chain.filter(exchange);
        }

    }

    private String fetchLBRoute(String path, String hostname) {
        //onramp /jobs/printjobs/
        if (path.contains(ONRAMP+PRINT_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(PRINT_JOB_URI);
        }
        else if (path.contains(ONRAMP+PRINT_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(PRINT_JOB_URI);
        }
        //eprintcenter /jobs/printjobs/
        else if (path.contains(EPRINT_CENTER+PRINT_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(PRINT_JOB_URI);
        }
        else if (path.contains(EPRINT_CENTER+PRINT_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(PRINT_JOB_URI);
        }
        //onramp /devices/printers/
        else if (path.contains(ONRAMP+DEVICE_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DEVICE_JOB_URI);
        }
        else if (path.contains(ONRAMP+DEVICE_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DEVICE_JOB_URI);
        }
        //eprintcenter /devices/printers/
        else if (path.contains(EPRINT_CENTER+DEVICE_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DEVICE_JOB_URI);
        }
        else if (path.contains(EPRINT_CENTER+DEVICE_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DEVICE_JOB_URI);
        }
        //onramp /jobs/scanjobs/
        else if (path.contains(ONRAMP+SCAN_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(SCAN_JOB_URI);
        }
        else if (path.contains(ONRAMP+SCAN_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(SCAN_JOB_URI);
        }
        //onramp /jobs/deliveryonlyjobs/
        else if (path.contains(ONRAMP+DELIVERY_ONLY_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(DELIVERY_ONLY_JOB_URI);
        }
        else if (path.contains(ONRAMP+DELIVERY_ONLY_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(DELIVERY_ONLY_JOB_URI);
        }
        //onramp /jobs/renderjobs/
        else if (path.contains(ONRAMP+RENDER_JOB_URI) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(RENDER_JOB_URI);
        }
        else if (path.contains(ONRAMP+RENDER_JOB_URI) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(RENDER_JOB_URI);
        }
        //offramp /Printers
        else if (path.contains(OFFRAMP+OFFRAMP_PRINTERS) && hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findAwsRouteUri(OFFRAMP_PRINTERS);
        }
        //onramp /tokens/session/token
        else if (path.contains(OFFRAMP+OFFRAMP_PRINTERS) && !hostname.contains(AMAZON_AWS)) {
            return gen1DeviceService.findNgdcRouteUri(OFFRAMP_PRINTERS);
        }
        //onramp helathcheck
        else if (path.contains(ONRAMP+HEALTHCHECK)) {
            return gen1DeviceService.findNgdcRouteUri(ONRAMP+HEALTHCHECK);
        }
        //eprintcenter helathcheck
        else if (path.contains(EPRINT_CENTER+HEALTHCHECK)) {
            return gen1DeviceService.findNgdcRouteUri(EPRINT_CENTER+HEALTHCHECK);
        }
        //offramp helathcheck
        else if (path.contains(OFFRAMP+HEALTHCHECK)) {
            return gen1DeviceService.findNgdcRouteUri(OFFRAMP+HEALTHCHECK);
        }
        else if (path.contains(ONRAMP + SESSION_TOKEN)) {
            return gen1DeviceService.findNgdcRouteUri(SESSION_TOKEN);
        }
        //onramp /tokens/session/secret
        else if (path.contains(ONRAMP + SCERET_TOKEN)) {
            return gen1DeviceService.findNgdcRouteUri(SCERET_TOKEN);
        }
        // onramp/version
        else if (path.contains(ONRAMP+VERSION_)) {
            return gen1DeviceService.findNgdcRouteUri(ONRAMP+VERSION_);
        }
        // eprintcenter/version
        else if (path.contains(EPRINT_CENTER+VERSION)) {
            return gen1DeviceService.findNgdcRouteUri(EPRINT_CENTER+VERSION);
        }
        else{
            return "no route found";
        }
    }

    @Override
    public int getOrder() {
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
    }

}