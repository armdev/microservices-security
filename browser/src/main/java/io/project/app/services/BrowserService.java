package io.project.app.services;

import io.project.app.dto.Login;
import io.project.app.dto.User;
import io.project.app.dto.Wiki;
import io.project.app.util.GsonConverter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class BrowserService {
    
    private final String BASE_URL = "http://zuul:8079/gateway/micro";
    
    @Autowired
    private CloseableHttpClient httpClient;
    
    public Integer userRegistration(User model) {
        log.info("BrowserService: UserRegistration start ");
        Integer status = 0;
        //  try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        log.info("BrowserService: UserRegistration ");
        HttpPost request = new HttpPost(BASE_URL + "/register/api/v2/users/register");
        
        String toJson = GsonConverter.to(model);
        StringEntity params = new StringEntity(toJson, "UTF-8");
        request.addHeader("content-type", "application/json;charset=UTF-8");
        request.addHeader("charset", "UTF-8");
        request.setEntity(params);
        long startTime = System.currentTimeMillis();
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            log.info("UserRegistration status code " + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // returnedModel = GsonConverter.from(EntityUtils.toString(httpResponse.getEntity()), String.class);
                status = httpResponse.getStatusLine().getStatusCode();
            } else {
                status = httpResponse.getStatusLine().getStatusCode();
                log.info("Status is not 200 " + status);
            }
            
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("UserRegistration request/response time in milliseconds: " + elapsedTime);
//        try {
//            //   } catch (IOException e) {
//            // log.error("Exception caught.", e);
//            // }
//          //  httpClient.close();
//        } catch (IOException ex) {
//            log.error(ex.getLocalizedMessage());
//        }
        return status;
    }
    
    public User userLogin(Login model) {
        log.info("UserAuthClient: userLogin called ");
        User returnedModel = new User();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/auth/api/v2/users/login");
            String toJson = GsonConverter.to(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("userLogin httpResponse.getStatusLine().getStatusCode() " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    returnedModel = GsonConverter.from(EntityUtils.toString(httpResponse.getEntity()), User.class);
                    Header firstHeader = httpResponse.getFirstHeader("AUTH-TOKEN");
                    log.info("Find token from server: " + firstHeader.getName() + " " + firstHeader.getValue());
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("userLogin request/response time in milliseconds: " + elapsedTime);
            
        } catch (IOException e) {
            log.error("Exception caught.", e);
        }
        return returnedModel;
    }
    
    
     public String userLoginReturnToken(Login model) {
        log.info("userLoginReturnToken: userLogin called ");
        String token = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/auth/api/v2/users/login");
            String toJson = GsonConverter.to(model);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("userLoginReturnToken httpResponse.getStatusLine().getStatusCode() " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    //returnedModel = GsonConverter.from(EntityUtils.toString(httpResponse.getEntity()), User.class);
                    Header firstHeader = httpResponse.getFirstHeader("AUTH-TOKEN");
                    log.info("Find token from server: " + firstHeader.getName() + " " + firstHeader.getValue());
                    token = firstHeader.getValue();
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("userLoginReturnToken request/response time in milliseconds: " + elapsedTime);
            
        } catch (IOException e) {
            log.error("userLoginReturnToken Exception caught.", e);
        }
        return token;
    }
    
    
     public String postWiki(Wiki wiki, String token) {
        log.info("postWiki: userLogin called ");
        String id = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/wiki/api/v2/wikis/wiki");
            String toJson = GsonConverter.to(wiki);
            StringEntity params = new StringEntity(toJson, "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.addHeader("AUTH-TOKEN", token);
            request.setEntity(params);
            CloseableHttpResponse response = httpClient.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            long startTime = System.currentTimeMillis();
            try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
                log.info("postWiki httpResponse.getStatusLine().getStatusCode() " + httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    id = GsonConverter.from(EntityUtils.toString(httpResponse.getEntity()), String.class);                    
                }
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("postWiki request/response time in milliseconds: " + elapsedTime);
            
        } catch (IOException e) {
            log.error("postWiki Exception caught.", e);
        }
        return id;
    }
    
}
