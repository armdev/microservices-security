package io.project.app.resources;

import io.jsonwebtoken.Claims;
import io.project.app.domain.Wiki;
import io.project.app.dto.ResponseMessage;
import io.project.app.security.TokenProvider;
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
    private TokenProvider tokenProvider;

    @Autowired
    private WikiService userService;

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/wiki", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> post(@RequestBody Wiki wiki,
            @RequestHeader(value = "zuul-token", required = true) String zuulToken,
            @RequestHeader(value = "auth-token", required = true) String authToken) {

        log.info("Zuul Token " + zuulToken);

        Boolean authTokenValidation = tokenProvider.validateToken(authToken);

        if (!authTokenValidation) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Auth Token is not valid"));
        }

        Boolean validateToken = tokenProvider.validateToken(zuulToken);

        if (!validateToken) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Zuul Token is not valid"));
        }

        Claims allClaimsFromToken = tokenProvider.getAllClaimsFromToken(authToken);
        if (allClaimsFromToken != null && allClaimsFromToken.getSubject() != null) {
            Try<String> verifyUser = authService.verifyUser(allClaimsFromToken.getSubject());

            if (verifyUser.isFailure()) {
                log.info("User verify by email is failed");
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
