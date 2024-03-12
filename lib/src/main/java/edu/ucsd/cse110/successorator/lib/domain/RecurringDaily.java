package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

public class RecurringDaily implements RecurringType {

    public RecurringDaily() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.DAILY;
    }

    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate currIterDate, SuccessDate checkDate) {
        if(startDate.toJavaDate().compareTo(currIterDate.toJavaDate())  > 0){
            return false;
        }
        return true;
    }

    @Override
    public String getDescription(Date startDate) {
        return "Daily starting on " + startDate.toString();
    }

}

