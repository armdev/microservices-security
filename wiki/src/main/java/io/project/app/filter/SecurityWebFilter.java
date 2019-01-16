package io.project.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //log.info("SecurityWebFilter FILTER STARTED");
        boolean containsKey = exchange.getRequest().getHeaders().containsKey("AUTH-TOKEN");
        log.info("containsKey ? " + containsKey);

        if (!exchange.getRequest().getQueryParams().containsKey("soccer")) {
            log.error("RETURN UNAUTHORIZED, NO SOCCER ");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        if (!exchange.getRequest().getHeaders().containsKey("AUTH-TOKEN")) {
            log.error("RETURN UNAUTHORIZED ");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }
}
