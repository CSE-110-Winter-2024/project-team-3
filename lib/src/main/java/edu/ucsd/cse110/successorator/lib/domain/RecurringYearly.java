package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;

public class RecurringYearly implements RecurringType{

    public RecurringYearly() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.YEARLY;
    }


    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate currIterDate, SuccessDate checkDate) {
        if(startDate.toJavaDate().compareTo(currIterDate.toJavaDate()) <= 0){
            if(startDate.getMonth() == currIterDate.getMonth()) {
                if(startDate.getDay() == currIterDate.getDay()){
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