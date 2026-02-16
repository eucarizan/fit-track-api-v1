package dev.nj.fta.developer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends ListCrudRepository<Developer, Long> {
    Optional<Developer> findByEmail(String email);
}
