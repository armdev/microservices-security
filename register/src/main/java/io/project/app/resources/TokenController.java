package io.project.app.resources;

import io.project.app.dto.ResponseMessage;
import io.project.app.security.Device;
import io.project.app.security.TimeProvider;
import io.project.app.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/tokens")
@Slf4j
public class TokenController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TimeProvider timeProvider;

    @GetMapping(path = "/verify/refresh/mobile/{token}", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> refreshMobileToken(@PathVariable String token
    ) {

        log.info("!!!!!!refreshMobileToken called!!!!!!");

        if (token == null) {
            log.error("Mobile Did not find token in the request");
            return ResponseEntity.badRequest().body(new ResponseMessage("Please put token in the request"));
        }

        boolean validateToken = tokenProvider.validateToken(token);

        if (!validateToken) {
            log.info("Mobile: Token is not valid!!!!!!");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage("Token is not valid"));
        }

        log.info("Mobile refreshMobileToken!!!!!!");
        final Device device = new Device(false, false, true);
        String generateToken = tokenProvider.generateToken(tokenProvider.getAllClaimsFromToken(token).getSubject(), device);
        log.info("Mobile token is accepted and refreshed");
        return ResponseEntity.accepted().body(generateToken);
    }

    @GetMapping(path = "/verify/refresh/{token}", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> refreshWebToken(@PathVariable String token
    ) {

        log.info("!!!!!!refreshWebToken called!!!!!!");

        if (token == null) {
            log.error("Did not find token in the request");
            return ResponseEntity.badRequest().body(new ResponseMessage("Please put token in the request"));
        }

        boolean validateToken = tokenProvider.validateToken(token);

        if (!validateToken) {
            log.info("Web !!!!!!Token is not valid!!!!!!");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage("Token is not valid"));
        }

        log.info("WEB !!!!!!refreshWebToken!!!!!!");
        final Device device = new Device(true, false, false);
        String generateToken = tokenProvider.generateToken(tokenProvider.getAllClaimsFromToken(token).getSubject(), device);
        log.info("WEB token is accepted and refreshed");
        return ResponseEntity.accepted().body(generateToken);

    }

    @GetMapping(path = "/verify/{token}", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> verifyToken(@PathVariable String token
    ) {

        log.info("!!!!!!verifyToken called!!!!!!");

        if (token == null) {
            log.error("Did not find token in the request");
            return ResponseEntity.badRequest().body(new ResponseMessage("Please put token in the request"));
        }

        boolean validateToken = tokenProvider.validateToken(token);

        if (!validateToken) {
            log.info("!!!!!!Token is not valid!!!!!!");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage("Token is not valid"));
        }

        log.info("!!!!!!Token is accepted!!!!!!");
        return ResponseEntity.accepted().body(tokenProvider.getAllClaimsFromToken(token));

    }

    @GetMapping(path = "/generate/test/token")
    @CrossOrigin
    public String generate() {
        log.info("generate test token");
        final Device device = new Device(true, false, false);
        String generateToken = tokenProvider.generateToken("a@gmail.com", device);
        log.info("Token is accepted and refreshed");
        return generateToken;
    }

}
