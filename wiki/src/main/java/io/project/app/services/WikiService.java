package io.project.app.services;

import io.project.app.domain.Wiki;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import io.project.app.repositories.WikiRepository;
import java.util.List;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class WikiService {

    @Autowired
    private WikiRepository wikiRepository;

    public Optional<Wiki> addNewWiki(Wiki wiki) {
        Wiki addedWiki = wikiRepository.save(wiki);

        return Optional.ofNullable(addedWiki);
    }

    public Optional<List<Wiki>> findAll(String userId) {
        Optional<List<Wiki>> wikiList = wikiRepository.findAllByUserIdByOrderPublishDateDesc(userId);

        return wikiList;
    }

    public Optional<Wiki> findById(String id, String userId) {
        Optional<Wiki> wiki = wikiRepository.findByIdAndUserId(id, userId);

        return wiki;
    }

}
