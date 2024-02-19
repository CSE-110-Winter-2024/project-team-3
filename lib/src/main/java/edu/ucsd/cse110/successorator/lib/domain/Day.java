package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public class Day {
    private final @NonNull Subject<SuccessDate> successDate;
    private @NonNull SuccessDate oldDate;
    private final @NonNull GoalRepository goalRepository;

    @NonNull
    public Subject<SuccessDate> getSuccessDate() {
        return successDate;
    }

    @NonNull
    public GoalRepository getGoalRepository() {
        return goalRepository;
    }

    public Day(@NonNull Subject<SuccessDate> successDate, @NonNull GoalRepository goalRepository) {
        this.successDate = successDate;
        assert successDate.getValue() != null;
        this.oldDate = successDate.getValue();
        this.goalRepository = goalRepository;

        // whenever date advances, Day will clear out old goals
        successDate.observe(newDate -> {
            assert newDate != null;
            if (!newDate.getYear().equals(oldDate.getYear())
                    || !newDate.getMonth().equals(oldDate.getMonth())
                    || !newDate.getDay().equals(oldDate.getDay()) ){

                oldDate = newDate;

                var goals = goalRepository.findAll().getValue();
                assert goals != null;
                for (var goal : goals) {
                    if (goal.isCompleted()) {
                        goalRepository.remove(goal.getId());
                    }
                }

            }
        });
    }

}
