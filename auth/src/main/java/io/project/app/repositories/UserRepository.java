package io.project.app.repositories;

import io.project.app.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 *
 * @author armena
 */
@Repository
@Component
public interface UserRepository
        extends ReactiveCrudRepository<User, String> {

    Mono<User> findByEmailAndPassword(String email, String password);

}
