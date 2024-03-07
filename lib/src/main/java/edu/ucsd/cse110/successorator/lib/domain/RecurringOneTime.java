package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RecurringOneTime implements RecurringType{

    @Override
    public RepeatType getType() {
        return RepeatType.ONE_TIME;
    }

    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        return startDate.equals(checkDate);
    }

    @Override
    public String getDescription(Date startDate) {
        if (startDate == null) {
            return "Pending";
        } else {
            return "One-time on "+ startDate.toString();
        }
    }

}
