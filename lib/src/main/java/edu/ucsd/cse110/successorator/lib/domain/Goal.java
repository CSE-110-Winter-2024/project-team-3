package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Date;

public class Goal {
    private final @NonNull Integer id;
    private final @NonNull String name;
    private final @NonNull Boolean completed;
    private final @NonNull Date date;
    public final @NonNull GoalRecord recurringGoalRecord;

    public Goal(@NonNull String name, @NonNull Integer id, @NonNull Boolean completed,
                        @NonNull Date date, @NonNull GoalRecord recurringGoalRecord) {
        this.name = name;
        this.id = id;
        this.completed = completed;
        this.date = date;
        this.recurringGoalRecord = recurringGoalRecord;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Date getDate(){
        return date;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public boolean isCompleted(){
        return this.completed;
    }

    @NonNull
    public Goal withComplete(boolean newComplete) {
        return new Goal(name, id, newComplete, date, recurringGoalRecord);
    }

    @NonNull
    public Goal withId(int id) {
        return new Goal(name, id, completed, date, recurringGoalRecord);
    }

}
