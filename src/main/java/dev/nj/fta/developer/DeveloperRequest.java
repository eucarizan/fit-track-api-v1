package dev.nj.fta.developer;

import jakarta.validation.constraints.NotBlank;

public record DeveloperRequest(
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
}
