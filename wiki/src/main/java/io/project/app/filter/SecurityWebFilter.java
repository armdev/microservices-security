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
        log.info("SecurityWebFilter FILTER STARTED");
        if (!exchange.getRequest().getHeaders().containsKey("AUTH-TOKEN")) {
            log.error("RETURN BAD REQUEST");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          
        }

        return chain.filter(exchange);
    }
}
