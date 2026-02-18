package dev.nj.fta.developer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    private final PasswordEncoder passwordEncoder;

    public DeveloperMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Developer toEntity(DeveloperRequest request) {
        return new Developer(
                request.email(),
                passwordEncoder.encode(request.password())
        );
    }

    public DeveloperResponse toResponse(Developer developer) {
        return new DeveloperResponse(
                developer.getId(),
                developer.getEmail()
        );
    }
}
