package dev.nj.fta.fitnessdata;

import java.util.List;

public interface FitnessDataService {

    void createFitnessData(FitnessData fitnessData);

    List<FitnessDataResponse> getAllData();
}
