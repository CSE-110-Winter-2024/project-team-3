package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

public interface RecurringType {
    public RepeatType getType();

    /**
     "do hw" weekly 3/3/2024
     "go to school" weekly 3/3/2024

     "do hw".ifDateMatchesRecurring(3/4/2024) -> false
     "do hw".ifDateMatchesRecurring(10/3/2024) -> true
     */
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate currIterDate, SuccessDate checkDate);

    public String getDescription(Date startDate);
}

