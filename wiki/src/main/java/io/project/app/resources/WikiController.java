package io.project.app.resources;

import io.project.app.domain.Wiki;
import io.project.app.dto.ResponseMessage;
import io.project.app.security.TokenProvider;
import io.project.app.services.WikiService;
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

    @PostMapping(path = "/wiki", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> post(@RequestBody Wiki wiki,
            @RequestHeader(value = "zuul-token", required = true) String zuulToken) {

        log.info("Zuul Token " + zuulToken);

        Boolean validateToken = tokenProvider.validateToken(zuulToken);

        if (!validateToken) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Zuul Token is not valid"));
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
