package dev.nj.fta.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperServiceImpl.class);

    private final DeveloperRepository developerRepository;

    public DeveloperServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }


    @Override
    public Long createDeveloper(Developer developer) {
        logger.debug("Creating developer: email={}", developer.getEmail());

        Developer saved = developerRepository.save(developer);
        logger.debug("Developer persisted: id={}", saved.getId());
        return saved.getId();
    }
}
