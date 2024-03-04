package edu.ucsd.cse110.successorator.lib.domain;

import java.util.ArrayList;
import java.util.Date;

interface RecurringType {
    enum RepeatType {
        ONE_TIME,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY
    };

    /**
     "do hw" weekly 3/3/2024
     "go to school" weekly 3/3/2024

     "do hw".ifDateMatchesRecurring(3/4/2024) -> false
     "do hw".ifDateMatchesRecurring(10/3/2024) -> true
     */
    public boolean ifDateMatchesRecurring(Date date);
    public RepeatType getType();
    public String getDescription();

}

