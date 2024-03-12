package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

public class RecurringMonthly implements RecurringType{

    public RecurringMonthly() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.MONTHLY;
    }


    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate currIterDate, SuccessDate checkDate) {
        if(startDate.toJavaDate().compareTo(currIterDate.toJavaDate()) <= 0){
            if(startDate.getDay() == currIterDate.getDay()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription(Date startDate) {
        return "Monthly starting on "+ startDate.toString();
    }

}