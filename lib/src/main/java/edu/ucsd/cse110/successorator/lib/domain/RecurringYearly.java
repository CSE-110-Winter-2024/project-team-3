package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class RecurringYearly implements RecurringType{

    public RecurringYearly() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.YEARLY;
    }


    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        if(startDate.toJavaDate().compareTo(checkDate.toJavaDate()) <= 0){
            if(startDate.getMonth() == checkDate.getMonth()) {
                if(startDate.getDay() == checkDate.getDay()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription(Date startDate) {
        return "Yearly starting on "+ startDate.toString();
    }

}