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
}
