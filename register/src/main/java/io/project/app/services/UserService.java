package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.repositories.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> registerNewUser(User user) {
        Optional<User> findByEmail = userRepository.findByEmail(user.getEmail());
        if (findByEmail.isPresent()) {
            return Optional.empty();
        }
        user.setId(null);
        User registeredUser = userRepository.save(user);
        return Optional.ofNullable(registeredUser);
    }

}
