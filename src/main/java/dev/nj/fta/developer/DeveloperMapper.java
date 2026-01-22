package dev.nj.fta.developer;

import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {
    public Developer toEntity(DeveloperRequest request) {
        return new Developer(
                request.email(),
                request.password()
        );
    }

    public DeveloperResponse toResponse(Developer developer) {
        return new DeveloperResponse(
                developer.getId(),
                developer.getEmail()
        );
    }
}
