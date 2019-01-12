package io.project.app.services;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

/**
 *
 * @author armena
 */
@Service
@Component
@RequestScope
@Slf4j
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    public Try<String> register(String message, String authToken) {
        log.info("sendMessageToWiki " + message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-AUTH-TOKEN", authToken);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        Try<String> returnedMessage = null;
        // for (int i = 0; i < 1000; i++) {
        returnedMessage = Try.of(() -> restTemplate.postForObject("http://zuul:8079/gateway/web/wiki/api/v2/wikis/message", entity, String.class));
        log.info("sendMessageToWiki TOKEN " + returnedMessage);
        // }
        return returnedMessage;
    }

    public Try<String> login(String message, String authToken) {
        log.info("sendMessageToWiki " + message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-AUTH-TOKEN", authToken);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        Try<String> returnedMessage = null;
        // for (int i = 0; i < 1000; i++) {
        returnedMessage = Try.of(() -> restTemplate.postForObject("http://zuul:8079/gateway/web/wiki/api/v2/wikis/message", entity, String.class));
        log.info("sendMessageToWiki TOKEN " + returnedMessage);
        // }
        return returnedMessage;
    }

    public Try<String> postWiki(String message, String authToken) {
        log.info("sendMessageToWiki " + message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-AUTH-TOKEN", authToken);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        Try<String> returnedMessage = null;
        // for (int i = 0; i < 1000; i++) {
        returnedMessage = Try.of(() -> restTemplate.postForObject("http://zuul:8079/gateway/web/wiki/api/v2/wikis/message", entity, String.class));
        log.info("sendMessageToWiki TOKEN " + returnedMessage);
        // }
        return returnedMessage;
    }

    public Try<String> getAllMyWikiPages(String message, String authToken) {
        log.info("sendMessageToWiki " + message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-AUTH-TOKEN", authToken);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        Try<String> returnedMessage = null;
        // for (int i = 0; i < 1000; i++) {
        returnedMessage = Try.of(() -> restTemplate.postForObject("http://zuul:8079/gateway/web/wiki/api/v2/wikis/message", entity, String.class));
        log.info("sendMessageToWiki TOKEN " + returnedMessage);
        // }
        return returnedMessage;
    }

    public Try<String> openMyWikiById(String message, String authToken) {
        log.info("sendMessageToWiki " + message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-AUTH-TOKEN", authToken);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        Try<String> returnedMessage = null;
        // for (int i = 0; i < 1000; i++) {
        returnedMessage = Try.of(() -> restTemplate.postForObject("http://zuul:8079/gateway/web/wiki/api/v2/wikis/message", entity, String.class));
        log.info("sendMessageToWiki TOKEN " + returnedMessage);
        // }
        return returnedMessage;
    }

}
