package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Date;

public class GoalRecord {


    private final @NonNull String name;
    private final @NonNull Integer id;
    private final @NonNull Date startDate;
    public final @NonNull RecurringType recurringType;

    public GoalRecord(@NonNull String name, @NonNull Integer id,
                      @NonNull Date startDate, @NonNull RecurringType recurringType) {
        this.name = name;
        this.id = id;
        this.startDate = startDate;
        this.recurringType = recurringType;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Date getStartDate(){
        return startDate;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public GoalRecord withId(int id) {
        return new GoalRecord(name, id, startDate, recurringType);
    }


    /**
     "do hw" weekly 3/3/2024
     "go to school" weekly 3/3/2024

     "do hw".ifDateMatchesRecurring(3/4/2024) -> false
     "do hw".ifDateMatchesRecurring(10/3/2024) -> true
     */
    public boolean ifDateMatchesRecurring(Date checkDate) {
        return recurringType.ifDateMatchesRecurring(startDate, checkDate);
    }

    public String getDescription() {
        return recurringType.getDescription(startDate);
    }

    public RecurringType.RepeatType getType() {
        return recurringType.getType();
    }


    public Goal toGoal() {
        return new Goal(name, id, false, startDate, this);
    }
}
