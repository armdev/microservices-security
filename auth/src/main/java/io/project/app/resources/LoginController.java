package io.project.app.resources;

import io.project.app.dto.ResponseMessage;
import io.project.app.security.Device;
import io.project.app.security.TimeProvider;
import io.project.app.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/tokens")
@Slf4j
public class LoginController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TimeProvider timeProvider;

    @PostMapping(path = "/login", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> login() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("MP-AUTH-TOKEN", this.generate());
        headers.add("Authorization", this.generate());

        return ResponseEntity.ok().headers(headers).body("some user model");

    }

    public String generate() {
        log.info("generate test token");
        final Device device = new Device(true, false, false);
        String generateToken = tokenProvider.generateToken("a@gmail.com", device);
        log.info("Token is accepted and refreshed");
        return generateToken;
    }

}
