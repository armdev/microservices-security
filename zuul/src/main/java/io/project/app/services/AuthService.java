package io.project.app.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.project.app.dto.Claim;

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

    @HystrixCommand(fallbackMethod = "verifyTokenBackup")
    public Try<Claim> verifyToken(String token) {
        log.info("LABEL: VerifyToken: with passed token " + token);
        Try<Claim> col = Try.of(() -> restTemplate.getForObject("http://auth/api/v2/tokens/verify/{token}", Claim.class, token));
        if (!col.isSuccess()) {
            log.error("LABEL: Fail " + token);
            return Try.failure(col.getCause());
        }
        if (col.isSuccess()) {
            log.info("TOKEN CHECK IS  SUCCESS ");

        }
        return col;
    }

    public Try<Claim> verifyTokenBackup(String token) {
        log.info("LABEL: VerifyToken: with passed token " + token);
        Try<Claim> col = Try.of(() -> restTemplate.getForObject("http://auth:5001/api/v2/tokens/verify/{token}", Claim.class, token));
        if (!col.isSuccess()) {
            log.error("LABEL: Fail " + token);
            return Try.failure(col.getCause());
        }
        if (col.isSuccess()) {
            log.info("TOKEN CHECK IS  SUCCESS ");

        }
        return col;
    }

}
