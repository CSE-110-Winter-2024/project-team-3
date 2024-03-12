package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

public class Goal {
    private final @NonNull Integer id;
    private final @NonNull String name;
    private final @NonNull Boolean currCompleted;
    private final @NonNull Boolean nextCompleted;
    private final Date assignDate;
    private final Date currIterDate;
    public final @NonNull FocusType focus;
    public final @NonNull RecurringType recurringType;

    public Goal(@NonNull String name, @NonNull Integer id, @NonNull Boolean currCompleted,
                @NonNull Boolean nextCompleted, Date assignDate, Date currIterDate,
                @NonNull RepeatType repeatType, @NonNull FocusType focus) {
        this.name = name;
        this.id = id;
        this.currCompleted = currCompleted;
        this.assignDate = assignDate;
        this.nextCompleted = nextCompleted;
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

    @NonNull
    public boolean getNextCompleted() { return this.nextCompleted; }

    @NonNull
    public Integer getId() {
        return id;
    }

    public Date getCurrIterDate() { return currIterDate; }

    @NonNull
    public boolean getCurrCompleted(){
        return this.currCompleted;
    }

    @NonNull
    public Goal withCurrComplete(boolean newComplete) {
        return new Goal(name, id, newComplete, nextCompleted, assignDate, currIterDate, recurringType.getType(), focus);
    }

    @NonNull
    public Goal withNextComplete(boolean newNextComplete) {
        return new Goal(name, id, currCompleted, newNextComplete, assignDate, currIterDate, recurringType.getType(), focus);
    }


    @NonNull
    public FocusType get_focus(){return this.focus;}

    @NonNull
    public Goal withId(int id) {
        return new Goal(name, id, currCompleted, nextCompleted, assignDate, currIterDate, recurringType.getType(), focus);
    }


    /**
     "do hw" weekly 3/3/2024
     "go to school" weekly 3/3/2024

     "do hw".ifDateMatchesRecurring(3/4/2024) -> false
     "do hw".ifDateMatchesRecurring(10/3/2024) -> true
     */
    public boolean ifDateMatchesRecurring(SuccessDate checkDate) {
        if (assignDate == null) return false;

        if ( currIterDate != null ) {
            int deltaDays = SuccessDate.fromJavaDate(currIterDate).toJavaDate().compareTo(checkDate.toJavaDate());
            if (deltaDays > 0) {
                return false;
            } else if (deltaDays == 0) {
                return recurringType.ifDateMatchesRecurring(SuccessDate.fromJavaDate(assignDate), checkDate);
            } else {
                return true;
            }
        }

        return recurringType.ifDateMatchesRecurring(SuccessDate.fromJavaDate(assignDate), checkDate);
    }

    public boolean ifDateMatchesRecurring_NoRollOver(SuccessDate checkDate) {
        if (assignDate == null) return false;

        if ( currIterDate != null ) {
            int deltaDays = SuccessDate.fromJavaDate(currIterDate).toJavaDate().compareTo(checkDate.toJavaDate());
            if (deltaDays > 0) {
                return false;
            } else if (deltaDays == 0) {
                return recurringType.ifDateMatchesRecurring(SuccessDate.fromJavaDate(assignDate), checkDate);
            }
        }

        return recurringType.ifDateMatchesRecurring(SuccessDate.fromJavaDate(assignDate), checkDate);
    }

    public String getDescription() {
        return recurringType.getDescription(assignDate);
    }

    public RepeatType getType() {
        return recurringType.getType();
    }

    public Date calculateNextRecurring(SuccessDate todayDateTemp) {
        return recurringType.calculateNextRecurring(SuccessDate.fromJavaDate(assignDate), todayDateTemp);
    }

    public Goal withCurrIterDate(Date currIterDate) {
        return new Goal(name, id, currCompleted, nextCompleted, assignDate,
                currIterDate, recurringType.getType(), focus);
    }

    public Goal withAssignDate(Date javaDate) {
        return new Goal(name, id, currCompleted, nextCompleted, javaDate,
                currIterDate, recurringType.getType(), focus);
    }
}
