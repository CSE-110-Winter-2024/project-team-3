package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class RecurringWeekly implements RecurringType{

    public RecurringWeekly() {

    }

    private int getWeekInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    @Override
    public RepeatType getType() {
        return RepeatType.WEEKLY;
    }


    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        int thisWeekInt = startDate.getDayOfWeek();
        int checkWeekInt = checkDate.getDayOfWeek();
        return thisWeekInt == checkWeekInt;
    }

    @Override
    public String getDescription(Date startDate) {
        int weekInt = getWeekInt(startDate);
        return "Weekly on "+ DayOfWeek.of(weekInt).toString();
    }

}
