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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/users")
@Slf4j
public class LoginController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> login(@RequestBody Login login) {

        Optional<User> loggedUser = userService.login(login);

        if (loggedUser.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            final Device device = new Device(true, false, false);
            headers.add("AUTH-TOKEN", tokenProvider.generateToken(loggedUser.get().getEmail(), device));
            headers.add("Authorization", tokenProvider.generateToken(loggedUser.get().getEmail(), device));

            return ResponseEntity.ok().headers(headers).body(loggedUser.get());
        }

        if (!loggedUser.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("User does not exist"));
        }

        return ResponseEntity.notFound().build();

    }

}
