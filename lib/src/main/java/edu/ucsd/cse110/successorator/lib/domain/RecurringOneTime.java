package edu.ucsd.cse110.successorator.lib.domain;

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

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {
        return new Date(Long.MAX_VALUE);
    }
}
