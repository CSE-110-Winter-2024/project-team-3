package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class Day {
    private final @NonNull MutableSubject<SuccessDate> successDate;
    private @NonNull SuccessDate oldDate;
    private final @NonNull GoalRepository goalRepository;

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
