package io.project.app.resources;

import io.jsonwebtoken.Claims;
import io.project.app.domain.Wiki;
import io.project.app.dto.ResponseMessage;
import io.project.app.security.AuthTokenProvider;
import io.project.app.security.ZuulTokenValidator;
import io.project.app.services.AuthService;
import io.project.app.services.WikiService;
import io.vavr.control.Try;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/wikis")
@Slf4j
public class WikiController {

    @Autowired
    private AuthTokenProvider tokenProvider;

    @Autowired
    private ZuulTokenValidator zuulTokenValidator;

    @Autowired
    private WikiService userService;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/wiki", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> post(@RequestBody Wiki wiki,
            @RequestHeader(value = "zuul-token", required = true) String zuulToken,
            @RequestHeader(value = "auth-token", required = true) String authToken,
            @RequestHeader(value = "x-forwarded-host", required = false) String forwardedHost,
            @RequestHeader(value = "user-agent", required = false) String userAgent,
            @RequestHeader(value = "x-forwarded-prefix", required = false) String forwardedPrefix,
            @RequestHeader(value = "host", required = false) String host
    ) {

        log.info("x-forwarded-host  " + forwardedHost);
        log.info("userAgent  " + userAgent);
        log.info("forwardedPrefix  " + forwardedPrefix);
        log.info("host  " + host);

        //  log.info("Zuul Token " + zuulToken);
        Boolean authTokenValidation = tokenProvider.validateToken(authToken);

        if (!authTokenValidation) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Auth Token is not valid"));
        }

        Boolean validateToken = zuulTokenValidator.validateToken(zuulToken);

        if (!validateToken) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Zuul Token is not valid"));
        }

        Claims allClaimsFromToken = tokenProvider.getAllClaimsFromToken(authToken);
        if (allClaimsFromToken != null && allClaimsFromToken.getSubject() != null) {
            Try<String> verifyUser = authService.verifyUser(allClaimsFromToken.getSubject());

            if (verifyUser.isSuccess()) {
                log.info("SUCCESS: User verify by email is Valid");

            }

            if (verifyUser.isFailure()) {
                log.error("User verify by email is failed");
                return ResponseEntity.badRequest().body(new ResponseMessage("Could not add wiki"));
            }

        }

        Optional<Wiki> addedWiki = userService.addNewWiki(wiki);

        if (addedWiki.isPresent()) {
            log.info("Wiki is added");
            return ResponseEntity.ok().body(addedWiki.get().getId());
        }

        if (!addedWiki.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Could not add wiki"));
        }

        return ResponseEntity.badRequest().build();

    }

}
