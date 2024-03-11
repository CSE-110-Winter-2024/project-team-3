package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class RecurringMonthly implements RecurringType{

    public RecurringMonthly() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.MONTHLY;
    }


    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        if(startDate.toJavaDate().compareTo(checkDate.toJavaDate()) <= 0){
            if(startDate.getDay() == checkDate.getDay()){
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