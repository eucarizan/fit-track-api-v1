package dev.nj.fta.developer;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends ListCrudRepository<Developer, Long> {
}
