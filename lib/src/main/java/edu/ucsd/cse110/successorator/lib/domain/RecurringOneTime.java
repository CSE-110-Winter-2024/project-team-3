package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RecurringOneTime implements RecurringType{

    @Override
    public RecurringType.RepeatType getType() {
        return RepeatType.ONE_TIME;
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
