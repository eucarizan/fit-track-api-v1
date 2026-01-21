package dev.nj.fta.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperServiceImpl.class);

    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;

    public DeveloperServiceImpl(DeveloperRepository developerRepository, DeveloperMapper developerMapper) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
    }


    @Override
    public Long createDeveloper(DeveloperRequest request) {
        logger.debug("Creating developer: email={}", request.email());

        Developer saved = developerRepository.save(developerMapper.toEntity(request));
        logger.debug("Developer persisted: id={}", saved.getId());
        return saved.getId();
    }
}
