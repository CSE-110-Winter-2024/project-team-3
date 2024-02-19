package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MockDay extends Day{

    public MockDay(@NonNull Subject<SuccessDate> successDate, @NonNull GoalRepository goalRepository) {
        super(successDate, goalRepository);
    }

    public void nextDay() {
        SuccessDate oldDate = super.getSuccessDate().getValue();
        assert oldDate != null;
        SuccessDate newDate = oldDate.nextDay();
        super.getSuccessDate().setValue(newDate);
    }
}
