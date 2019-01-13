package io.project.app.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
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
        String homePage = this.serviceUrl("auth");
        Try<String> col = Try.of(() -> restTemplate.getForObject(homePage + "api/v2/validation/verify/user/{email}", String.class, email));
        if (!col.isSuccess()) {
            log.info("LABEL: verifyUser Fail " + email);
            return Try.failure(col.getCause());
        }
        return col;
    }

    @Autowired
    private EurekaClient discoveryClient;

    public String serviceUrl(String service) {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(service, false);
        log.info("HOME PAGE URL " + instance.getHomePageUrl());
        return instance.getHomePageUrl();
    }

}
