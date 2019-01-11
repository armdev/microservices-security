package io.project.app.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 *
 * @author armen
 */
@Configuration
public class CorsConfiguration {

    private static final String ALLOWED_HEADERS = "MP_FRONT, request, x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN, zuul-correlation-id, User-Agent, MP-AUTH-TOKEN";
    private static final String ALLOWED_METHODS = "GET, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "3600";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();

            if (CorsUtils.isCorsRequest(request)) {

                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
//                headers.add("Access-Control-Allow-Method", "GET");
//                headers.add("Access-Control-Allow-Method", "POST");
//                headers.add("Access-Control-Allow-Method", "DELETE");
//                headers.add("Access-Control-Allow-Method", "OPTIONS");
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
            }
            return chain.filter(ctx);
        };
    }
}
