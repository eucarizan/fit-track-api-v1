package dev.nj.fta.developer;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class DeveloperUserDetails implements UserDetails {

    private final Developer developer;

    DeveloperUserDetails(Developer developer) {
        this.developer = developer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return developer.getPassword();
    }

    @Override
    public String getUsername() {
        return developer.getEmail();
    }

    public Long getId() {
        return developer.getId();
    }
}
