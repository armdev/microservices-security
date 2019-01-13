package io.project.app.repositories;

import io.project.app.domain.Wiki;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armena
 */
@Repository
@Component
public interface WikiRepository
        extends MongoRepository<Wiki, String> {

    Optional<List<Wiki>> findAllByUserIdByOrderPublishDateDesc(String userId);
    
    Optional<Wiki> findByIdAndUserId(String id, String userId);

}
