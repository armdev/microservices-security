package io.project.app.services;


import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    public Try<String> verifyUser(String email) {
        log.info("LABEL: verifyUser: email " + email);
        Try<String> col = Try.of(() -> restTemplate.getForObject("http://auth/api/v2/validation/verify/user/{email}", String.class, email));
        if (!col.isSuccess()) {
            log.info("LABEL: verifyUser Fail " + email);
            return Try.failure(col.getCause());
        }
        return col;
    }

}
