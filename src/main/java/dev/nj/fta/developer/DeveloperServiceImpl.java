package dev.nj.fta.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        if (developerRepository.findByEmail(request.email()).isPresent()) {
            throw new DeveloperAlreadyExistsException();
        }
        Developer saved = developerRepository.save(developerMapper.toEntity(request));

        logger.debug("Developer persisted: id={}", saved.getId());
        return saved.getId();
    }

    @Override
    public DeveloperResponse getDeveloperById(Long id) {
        logger.debug("Getting developer: id={}", id);
        Optional<Developer> developer = developerRepository.findById(id);

        if (developer.isEmpty()) {
            throw new DeveloperNotFoundException("Developer with id " + id + " does not exist");
        }

        logger.debug("Developer found: id={}", developer.get().getId());
        return developerMapper.toResponse(developer.get());
    }
}
