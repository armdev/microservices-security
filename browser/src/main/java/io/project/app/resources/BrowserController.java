package io.project.app.resources;

import io.project.app.dto.Login;
import io.project.app.dto.ResponseMessage;
import io.project.app.dto.User;
import io.project.app.services.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/browser")
@Slf4j
public class BrowserController {

    @Autowired
    private BrowserService browserService;

    @PostMapping(path = "/register", produces = "application/json;charset=UTF-8")
    @CrossOrigin
    public ResponseEntity<?> registerAndLogin(@RequestBody User user) {

        Integer registeredUserStatus = browserService.userRegistration(user);

        if (registeredUserStatus == 200) {
            log.info("User registered we can try to login");
            User userLogin = browserService.userLogin(new Login(user.getEmail(), user.getPassword()));
            if (userLogin.getId() != null) {
                log.info("User id is present, so we are logged in");
                return ResponseEntity.ok().body(userLogin);
            } else {
                return ResponseEntity.badRequest().body(new ResponseMessage("Could not login user"));
            }
        }

        return ResponseEntity.badRequest().body(new ResponseMessage("Could not register new user"));

    }

}
