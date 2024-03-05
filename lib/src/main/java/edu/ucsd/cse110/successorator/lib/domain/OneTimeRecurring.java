package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OneTimeRecurring implements RecurringType{

    @Override
    public RecurringType.RepeatType getType() {
        return RecurringType.RepeatType.WEEKLY;
    }

    @Override
    public boolean ifDateMatchesRecurring(Date startDate, Date checkDate) {
        LocalDate startLocalDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate checkLocalDate = checkDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return (startLocalDate.getDayOfYear() == checkLocalDate.getDayOfYear())
                && (startLocalDate.getYear() == checkLocalDate.getYear());
    }

    @Override
    public String getDescription(Date startDate) {
        return "One-time on "+ startDate.toString();
    }

}
