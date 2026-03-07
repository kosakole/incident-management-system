package org.unibl.etf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("alert_route", r -> r.path("/api/alerts/**")
                        .uri("lb://ALERT-SERVICE"))
                .route("analysis_route", r -> r.path("/api/analysis/**")
                        .uri("lb://ANALYTICS-SERVICE"))
                .route("auth_route", r -> r.path("/oauth2/authorization/google")
                        .uri("lb://AUTH-SERVICE"))
                .route("incident_route", r -> r.path("/api/incidents/**")
                        .uri("lb://INCIDENT-SERVICE"))
                .route("moderation_route", r -> r.path("/api/moderation/**")
                        .uri("lb://MODERATION-SERVICE"))
                .route("user_route", r -> r.path("/api/users/**")
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}
