package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.dto.Login;
import io.project.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author armena
 */
@Service
@Component
public class UserService {
    
     @Autowired
     private UserRepository userRepository;
     
     public Mono<User> login(Login login){
         return userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());
     }
     
}
