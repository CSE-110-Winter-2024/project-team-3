package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {
        LocalDate tempDate = startDate.toJavaDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        while (Date.from(tempDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).compareTo(todayDateTemp.toJavaDate()) <= 0) {
            tempDate = tempDate.plusMonths(1);
        }

        return Date.from(tempDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}