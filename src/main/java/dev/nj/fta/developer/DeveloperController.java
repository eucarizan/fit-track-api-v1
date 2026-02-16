package dev.nj.fta.developer;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponse> getDeveloper(@PathVariable Long id, @AuthenticationPrincipal DeveloperUserDetails principal) {
        logger.info("GET /api/developers/id={}", id);

        if (!id.equals(principal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        DeveloperResponse response = developerService.getDeveloperById(id);
        return ResponseEntity.ok(response);
    }
}
