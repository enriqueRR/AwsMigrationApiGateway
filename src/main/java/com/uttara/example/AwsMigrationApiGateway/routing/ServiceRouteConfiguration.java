package com.uttara.example.AwsMigrationApiGateway.routing;

import com.uttara.example.AwsMigrationApiGateway.filter.RedirectionFilter;
import com.uttara.example.AwsMigrationApiGateway.utility.TsApiGatewayConstants;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Note: We want to keep this as an example of configuring a Route with a custom filter
 * <p>
 * This corresponds with the properties configuration we have
 */
@Configuration
public class ServiceRouteConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,RedirectionFilter redirectionFilter) {
/*        return builder.routes()
                .route(r -> r.path("/api/v1/**")
                        .setRequestHeader("testKey", "testValue")
                        .uri("URL"))
                .build();*/
/*        return builder.routes()
                .route(r->r.)*/

      return builder.routes()////  on ramp routes-----------
                .route(r->
                                r.path("/**")

                        .filters(f->{
                            return f.filter(redirectionFilter);
                        }).uri("http://localhost:8081")
//                        .id("AWS_ROUTE")
                )

                .route(r->r.path("/**")
                        .filters(f->{
                            return f.filter(redirectionFilter);
                        }).uri("http://localhost:8082")
//                        .id("NGDC_ROUTE")
                )
               .build();
    }
}
