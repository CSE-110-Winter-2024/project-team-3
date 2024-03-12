package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

public class Goal {
    private final @NonNull Integer id;
    private final @NonNull String name;
    private final @NonNull Boolean completed;
    private final Date assignDate;
    private final Date currIterDate;
    public final @NonNull String focus;
    public final @NonNull RecurringType recurringType;

    public Goal(@NonNull String name, @NonNull Integer id, @NonNull Boolean completed,
                Date assignDate, Date currIterDate, @NonNull RepeatType repeatType, @NonNull String focus) {
        this.name = name;
        this.id = id;
        this.completed = completed;
        this.assignDate = assignDate;
        this.currIterDate = currIterDate;
        this.recurringType = RecurringTypeFactory.create(repeatType);
        this.focus = focus;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public Date getAssignDate(){
        return assignDate;
    }

    @Nullable
    public  Date getCurrIterDate() { return currIterDate;}

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
        return new Goal(name, id, newComplete, assignDate, currIterDate, recurringType.getType(), focus);
    }

    @NonNull
    public String get_focus(){return this.focus;}

    @NonNull
    public Goal withId(int id) {
        return new Goal(name, id, completed, assignDate, currIterDate, recurringType.getType(), focus);
    }


    /**
     "do hw" weekly 3/3/2024
     "go to school" weekly 3/3/2024

     "do hw".ifDateMatchesRecurring(3/4/2024) -> false
     "do hw".ifDateMatchesRecurring(10/3/2024) -> true
     */
    public boolean ifDateMatchesRecurring(SuccessDate checkDate) {
        return recurringType.ifDateMatchesRecurring(SuccessDate.fromJavaDate(assignDate), SuccessDate.fromJavaDate(currIterDate), checkDate);
    }

    public String getDescription() {
        return recurringType.getDescription(assignDate);
    }

    public RepeatType getType() {
        return recurringType.getType();
    }

}
