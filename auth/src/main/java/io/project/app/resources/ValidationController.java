package io.project.app.resources;

import io.project.app.domain.User;
import io.project.app.dto.Login;
import io.project.app.dto.ResponseMessage;
import io.project.app.security.Device;
import io.project.app.security.TokenProvider;
import io.project.app.services.UserService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/validation")
@Slf4j
public class ValidationController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/verify/user/{email}", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> get(@PathVariable(name = "email", required = true) String email,
            @RequestHeader(value = "micro-token", required = false) String microToken) {

//        Boolean validateToken = tokenProvider.validateToken(microToken);
//
//        if (!validateToken) {
//            return ResponseEntity.badRequest().body(new ResponseMessage("Micro Token is not valid"));
//        }

        Optional<User> loggedUser = userService.findByEmail(email);

        if (loggedUser.isPresent()) {
            
            log.info("USER IS PRESENT, lets generate new token for him");

            final Device device = new Device(true, false, false);

            return ResponseEntity.ok().body(tokenProvider.generateToken(loggedUser.get().getEmail(), device));
        }

        if (!loggedUser.isPresent()) {
            log.error("User is not present");
            return ResponseEntity.badRequest().body(new ResponseMessage("User does not exist"));
        }

        return ResponseEntity.notFound().build();

    }

}
