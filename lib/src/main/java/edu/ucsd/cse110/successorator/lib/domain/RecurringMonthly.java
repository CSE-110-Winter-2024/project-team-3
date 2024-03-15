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
            SuccessDate tempDate = startDate;
            while (checkDate.toJavaDate().compareTo(tempDate.toJavaDate()) > 0) {
                tempDate = SuccessDate.fromJavaDate(calculateNextRecurring(startDate, tempDate));
            }
            if (tempDate.equals(checkDate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription(Date startDate) {
        return getStaticDescription(SuccessDate.fromJavaDate(startDate));
    }

    private SuccessDate getRecurringOfMonth(SuccessDate dateToMatch, int year, int month) {
        LocalDate dayForCalculation = (new SuccessDate(year, month, 1)).toLocalDate();

        // get the date to the correct week day
        while (SuccessDate.fromLocalDate(dayForCalculation).getDayOfWeek() != dateToMatch.getDayOfWeek()) {
            dayForCalculation = dayForCalculation.plusDays(1);
        }

        // now dayForCalculation is on the first _____ weekday

        // add weeks so that it is the correct week on month
        for (int i = 1; i < dateToMatch.getWeekOfMonth(); i++) {
            dayForCalculation = dayForCalculation.plusWeeks(1);
        }

        return SuccessDate.fromLocalDate(dayForCalculation);
    }

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {

        SuccessDate previousMonthRecurring;
        if (todayDateTemp.getMonth() - 1 <= 0) {
            previousMonthRecurring = getRecurringOfMonth(
                    startDate,
                    todayDateTemp.getYear() - 1,
                    12);
        } else {
            previousMonthRecurring = getRecurringOfMonth(
                    startDate,
                    todayDateTemp.getYear(),
                    todayDateTemp.getMonth() - 1);
        }

        SuccessDate currMonthRecurring = getRecurringOfMonth(
                startDate,
                todayDateTemp.getYear(),
                todayDateTemp.getMonth());

        if (previousMonthRecurring.toJavaDate().compareTo(todayDateTemp.toJavaDate()) > 0) {
            return previousMonthRecurring.toJavaDate();
        } else if (currMonthRecurring.toJavaDate().compareTo(todayDateTemp.toJavaDate()) > 0) {
            return currMonthRecurring.toJavaDate();
        } else {
            if (todayDateTemp.getMonth() + 1 > 12) {
                return getRecurringOfMonth(
                        startDate,
                        todayDateTemp.getYear() + 1,
                        1).toJavaDate();
            } else {
                return getRecurringOfMonth(
                        startDate,
                        todayDateTemp.getYear(),
                        todayDateTemp.getMonth() + 1).toJavaDate();
            }
        }
    }

    public static String getStaticDescription(SuccessDate startDate) {
        LocalDate startLocalDate = startDate.toLocalDate();
        LocalDate tempLocalDate = startDate.toLocalDate();
        int numberOccuranceOfWeek = 0;
        while (startLocalDate.getMonth().getValue() == tempLocalDate.getMonth().getValue()) {
            tempLocalDate = tempLocalDate.plusWeeks(-1);
            numberOccuranceOfWeek++;
        }
        return "monthly on every " + numberOccuranceOfWeek + "th " + startDate.getDayOfWeekString();
    }
}