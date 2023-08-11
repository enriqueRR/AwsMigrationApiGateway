package com.uttara.example.AwsMigrationApiGateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import java.util.Map;
import java.util.Optional;

import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
@Component
public class RedirectionFilter extends AbstractGatewayFilterFactory<RedirectionFilter.Config>  {

    private final Logger logger = LoggerFactory.getLogger(RedirectionFilter.class);


    @Autowired
    public RedirectionFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Map<String, String> headers = exchange.getRequest().getHeaders().toSingleValueMap();
            logger.info("---Inside redirection filter-------");
            String shardCode = headers.get(TsApiGatewayConstants.SHARD_CODE);
            logger.info("shardCode : "+shardCode);
            String hostname = headers.get(TsApiGatewayConstants.HOST_NAME);
            logger.info("hostname : "+hostname);
            System.out.println("shardCode : +"+shardCode+"  hostname :"+hostname);
            boolean isConfig = false;
          Config config1 = null;
            if (hostname!=null)
            {
                isConfig = hostname.contains("amazonaws") ? true : false;
                config1= new Config(isConfig);
            }
            else
            {
                config1= new Config(isConfig);
            }

            return chain.filter(exchange);
        },3);
    }


    @Validated
    public static class Config {
        boolean isGolden = true;


        public Config() {
        }

        public Config(boolean isGolden) {
            this.isGolden = isGolden;

        }

        public boolean isGolden() {
            return isGolden;
        }

        public  void setGolden(boolean value) {
            this.isGolden = value;
        }

    }
}
