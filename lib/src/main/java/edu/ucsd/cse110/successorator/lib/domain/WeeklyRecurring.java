package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class WeeklyRecurring implements RecurringType{
    private final Date startDate;

    public WeeklyRecurring(Date startDate) {
        this.startDate = startDate;
    }

    private int getWeekInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    @Override
    public boolean ifDateMatchesRecurring(Date date) {
        int thisWeekInt = getWeekInt(startDate);
        int checkWeekInt = getWeekInt(date);
        return thisWeekInt == checkWeekInt;
    }

    @Override
    public RepeatType getType() {
        return RepeatType.WEEKLY;
    }

    @Override
    public String getDescription() {
        int weekInt = getWeekInt(startDate);
        return "Weekly on "+DayOfWeek.of(weekInt).toString();
    }
}
