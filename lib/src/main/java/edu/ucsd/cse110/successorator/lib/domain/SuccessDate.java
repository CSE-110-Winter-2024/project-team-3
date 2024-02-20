package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SuccessDate {
    private final @NonNull Integer year;
    private final @NonNull Integer month;
    private final @NonNull Integer day;

    @NonNull
    public Integer getYear() {
        return year;
    }

    @NonNull
    public Integer getMonth() {
        return month;
    }

    @NonNull
    public Integer getDay() {
        return day;
    }

    public SuccessDate(@NonNull Integer year, @NonNull Integer month, @NonNull Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuccessDate)) return false;
        SuccessDate that = (SuccessDate) o;
        return Objects.equals(year, that.year) && Objects.equals(month, that.month) && Objects.equals(day, that.day);
    }

    // New method to get the day of the week
    @NonNull
    public String getDayOfWeek() {
        LocalDate date = LocalDate.of(year, month, day);
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @NonNull
    public SuccessDate nextDay() {
        LocalDate date = LocalDate.of(year, month, day);
        date = date.plusDays(1);
        return new SuccessDate(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
    }
}
