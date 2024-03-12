package edu.ucsd.cse110.successorator.lib.domain;

import java.time.ZoneId;
import java.util.Date;

public class RecurringDaily implements RecurringType {

    public RecurringDaily() {

    }

    @Override
    public RepeatType getType() {
        return RepeatType.DAILY;
    }

    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        if ((startDate.toJavaDate().compareTo(checkDate.toJavaDate())  > 0)){
            return false;
        }
        return true;
    }

    @Override
    public String getDescription(Date startDate) {
        return getStaticDescription(SuccessDate.fromJavaDate(startDate));
    }

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {
        return todayDateTemp.nextDay().toJavaDate();
    }


    public static String getStaticDescription(SuccessDate startDate) {
        return "daily";
    }
}

