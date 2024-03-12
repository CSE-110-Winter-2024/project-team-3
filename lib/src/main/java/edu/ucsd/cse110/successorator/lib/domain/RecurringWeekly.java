package edu.ucsd.cse110.successorator.lib.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
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
        return getStaticDescription(SuccessDate.fromJavaDate(startDate));
    }

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {
        LocalDate tempDate = startDate.toJavaDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        while (Date.from(tempDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).compareTo(todayDateTemp.toJavaDate()) <= 0) {
            tempDate = tempDate.plusWeeks(1);
        }
        return Date.from(tempDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String getStaticDescription(SuccessDate startDate) {
        return "weekly on " + startDate.getDayOfWeekString().substring(0, 3);
    }
}
