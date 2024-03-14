package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

public class RecurringTypeFactory {

    @NonNull
    public static RecurringType create(@NonNull RepeatType repeatType) {
        switch (repeatType) {
            case ONE_TIME:
                return new RecurringOneTime();
            case DAILY:
                return new RecurringDaily();
            case WEEKLY:
                return new RecurringWeekly();
            case MONTHLY:
                return new RecurringMonthly();
            case YEARLY:
                return new RecurringYearly();
        }
        return null;
    }
}
