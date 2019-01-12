package io.project.app.resources;

import io.project.app.domain.User;
import io.project.app.dto.ResponseMessage;
import io.project.app.services.UserService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @PostMapping(path = "/register", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> register(@RequestBody User user) {

        Optional<User> registeredUser = userService.registerNewUser(user);

        if (registeredUser.isPresent()) {
            log.info("User is registered");
            return ResponseEntity.ok().body(registeredUser.get().getId());
        }

        if (!registeredUser.isPresent()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Could not register user"));
        }

        return ResponseEntity.badRequest().build();

    }

}
