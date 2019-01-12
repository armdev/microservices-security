package io.project.app.resources;

import io.project.app.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/browser")
@Slf4j
public class BrowserController {

    @Autowired
    private TokenProvider tokenProvider;

   
    

}
