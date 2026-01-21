package dev.nj.fta.developer;

import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createDeveloper(@Valid @RequestBody DeveloperRequest request) {
        logger.info("POST /api/developers/signup: email={}", request.email());

        Long id = developerService.createDeveloper(request);
        return ResponseEntity.created(URI.create("/api/developers/" + id)).build();
    }
}
