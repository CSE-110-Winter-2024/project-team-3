package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RecurringYearly implements RecurringType{

    public RecurringYearly() {

    }


    @Override
    public RepeatType getType() {
        return RepeatType.YEARLY;
    }

    private boolean isLeapYear(int year) {
        assert year >= 1583; // not valid before this date.
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    @Override
    public boolean ifDateMatchesRecurring(SuccessDate startDate, SuccessDate checkDate) {
        if (isLeapYear(startDate.getYear()) && !isLeapYear(checkDate.getYear())) {
            if (checkDate.getMonth() == 3 && checkDate.getDay() == 1) {
                return true;
            }
        }

        if(startDate.toJavaDate().compareTo(checkDate.toJavaDate()) <= 0){
            if(startDate.getMonth().equals(checkDate.getMonth())) {
                if(startDate.getDay().equals(checkDate.getDay())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getDescription(Date startDate) {
        return getStaticDescription(SuccessDate.fromJavaDate(startDate));
    }

    private SuccessDate nextRecurring(SuccessDate startDate, SuccessDate fromDate) {
        if (isLeapYear(startDate.getYear())
            && startDate.getMonth() == 2 && startDate.getDay() == 29) {
            if (startDate.getMonth() < fromDate.getMonth()
                    || (startDate.getMonth().equals(fromDate.getMonth())
                        && ((!isLeapYear(fromDate.getYear()) && fromDate.getMonth() == 2 && fromDate.getDay() == 28) ? fromDate.getDay() + 1 : fromDate.getDay()) < 29)) {
                return new SuccessDate(fromDate.getYear(), startDate.getMonth(), isLeapYear(fromDate.getYear()) ? 29 : 28);
            } else {
                return new SuccessDate(fromDate.getYear() + 1, startDate.getMonth(), isLeapYear(fromDate.getYear() + 1) ? 29 : 28);
            }
        } else {
            if (startDate.getMonth() < fromDate.getMonth()
                    || (startDate.getMonth().equals(fromDate.getMonth()) && fromDate.getDay() < startDate.getDay())) {
                return new SuccessDate(fromDate.getYear(), startDate.getMonth(), startDate.getDay());
            } else {
                return new SuccessDate(fromDate.getYear() + 1, startDate.getMonth(), startDate.getDay());
            }
        }

    }

    @Override
    public Date calculateNextRecurring(SuccessDate startDate, SuccessDate todayDateTemp) {
        SuccessDate tempDate = startDate;
        while (tempDate.toJavaDate().compareTo(todayDateTemp.toJavaDate()) <= 0) {
            tempDate = nextRecurring(startDate, tempDate);
        }
        return tempDate.toJavaDate();
    }

    public static String getStaticDescription(SuccessDate startDate) {
        return "yearly on " + startDate.getMonthString().substring(0,3) + " " + startDate.getDay();
    }
}