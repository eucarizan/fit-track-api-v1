package dev.nj.fta.fitnessdata;

public record FitnessDataResponse(
        Long id,
        String username,
        String activity,
        int duration,
        int calories
) {
}
