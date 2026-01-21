package dev.nj.fta.developer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperController.class);

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> createDeveloper(@RequestBody Developer developer) {
        logger.info("POST /api/developers/signup: email={}", developer.getEmail());

        Long id = developerService.createDeveloper(developer);
        return ResponseEntity.created(URI.create("/api/developers/" + id)).build();
    }
}
