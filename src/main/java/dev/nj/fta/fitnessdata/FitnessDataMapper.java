package dev.nj.fta.fitnessdata;

import org.springframework.stereotype.Component;

@Component
public class FitnessDataMapper {
    public FitnessDataResponse toResponse(FitnessData fitnessData) {
        return new FitnessDataResponse(
                fitnessData.getId(),
                fitnessData.getUsername(),
                fitnessData.getActivity(),
                fitnessData.getDuration(),
                fitnessData.getCalories()
        );
    }
}
