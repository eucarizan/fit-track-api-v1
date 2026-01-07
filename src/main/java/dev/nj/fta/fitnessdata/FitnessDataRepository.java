package dev.nj.fta.fitnessdata;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessDataRepository extends ListCrudRepository<FitnessData, Long> {

    List<FitnessData> findAllByOrderByCreatedDesc();

}
