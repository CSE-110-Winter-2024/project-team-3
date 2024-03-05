package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class WeeklyRecurring implements RecurringType{

    public WeeklyRecurring() {

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
    public boolean ifDateMatchesRecurring(Date startDate, Date checkDate) {
        int thisWeekInt = getWeekInt(startDate);
        int checkWeekInt = getWeekInt(checkDate);
        return thisWeekInt == checkWeekInt;
    }

    @Override
    public String getDescription(Date startDate) {
        int weekInt = getWeekInt(startDate);
        return "Weekly on "+ DayOfWeek.of(weekInt).toString();
    }

}
