package io.project.app.filter;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest.Headers;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 *
 * @author armena
 */
@Component
@Slf4j
@Service
public class WikiFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest serverRequest,
            HandlerFunction<ServerResponse> handlerFunction) {
        log.info("WIKI FILTER STARTED");
        Headers headers = serverRequest.headers();
        List<String> header = headers.header("auth-token");

        String auth = header.get(0);

        if (auth == null) {
            log.error("Auth Token does not exist, server stop working");
            return ServerResponse.status(FORBIDDEN).build();
        }

        return handlerFunction.handle(serverRequest);
    }
}
