package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class Day {
    private final @NonNull MutableSubject<SuccessDate> successDate;
    private @NonNull SuccessDate oldDate;
    private final @NonNull GoalRepository goalRepository;
    private Subject<List<Goal>> goalss;

    @NonNull
    public MutableSubject<SuccessDate> getSuccessDate() {
        return successDate;
    }

    @NonNull
    public GoalRepository getGoalRepository() {
        return goalRepository;
    }


    public Day(@NonNull MutableSubject<SuccessDate> successDate, @NonNull GoalRepository goalRepository) {
        this.successDate = successDate;
        assert successDate.getValue() != null;
        this.oldDate = successDate.getValue();
        this.goalRepository = goalRepository;
        this.goalss = goalRepository.findAll();

    }

}
