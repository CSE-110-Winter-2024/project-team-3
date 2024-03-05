package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

public class Day {
    private final @NonNull SuccessDate date;
    private final @NonNull ListOfGoals goalRepository;

    @NonNull
    public SuccessDate getDate() {
        return date;
    }

    @NonNull
    public ListOfGoals getGoalRepository() {
        return goalRepository;
    }


    public Day(@NonNull SuccessDate date, @NonNull ListOfGoals goalRepository) {
        this.date = date;
        this.goalRepository = goalRepository;
    }

}
