package org.unibl.etf.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CookieToHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpCookie authCookie = exchange.getRequest().getCookies().getFirst("IS_MS");

        if (authCookie != null) {
            String token = authCookie.getValue();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                            .header("Authorization", "Bearer " + token)
                            .build())
                    .build();
            return chain.filter(mutatedExchange);
        }

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -100;
    }
}
