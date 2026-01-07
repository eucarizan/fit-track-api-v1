package dev.nj.fta.fitnessdata;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FitnessData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String username;

    String activity;

    int duration;

    int calories;

    public FitnessData() {}

    public FitnessData(String username, String activity, int duration, int calories) {
        this.username = username;
        this.activity = activity;
        this.duration = duration;
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getActivity() {
        return activity;
    }

    public int getDuration() {
        return duration;
    }

    public int getCalories() {
        return calories;
    }
}

