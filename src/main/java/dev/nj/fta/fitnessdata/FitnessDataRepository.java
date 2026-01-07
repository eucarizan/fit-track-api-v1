package dev.nj.fta.fitnessdata;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessDataRepository extends ListCrudRepository<FitnessData, Long> {
}
