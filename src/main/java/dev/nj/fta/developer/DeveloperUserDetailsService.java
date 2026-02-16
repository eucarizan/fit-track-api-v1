package dev.nj.fta.developer;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeveloperUserDetailsService implements UserDetailsService {

    private final DeveloperRepository developerRepository;

    public DeveloperUserDetailsService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Developer developer = developerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Developer not found: " + email));
        return new DeveloperUserDetails(developer);
    }
}
