package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.dto.Login;
import io.project.app.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armena
 */
@Service
@Component
public class UserService {
    
     @Autowired
     private UserRepository userRepository;
     
     public Optional<User> login(Login login){
         return userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());
     }
     
     public Optional<User> findByEmail(String email){
         return userRepository.findByEmail(email);
     }
     
     
     
}
