package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecurringDailyTest {

    @Test
    public void ifMatch() {
        RecurringDaily recurr = new RecurringDaily();

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 2),
                new SuccessDate(2024, 12, 2)
        ), true);

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 2),
                new SuccessDate(2024, 12, 3)
        ), true);

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 2),
                new SuccessDate(2025, 12, 3)
        ), true);

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 2),
                new SuccessDate(2024, 12, 1)
        ), false);


        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 2),
                new SuccessDate(2023, 12, 1)
        ), false);
    }

    @Test
    public void calculateNext() {
        RecurringDaily recurr = new RecurringDaily();

        assertEquals(recurr.calculateNextRecurring(
                new SuccessDate(2024, 12, 3),
                new SuccessDate(2024, 12, 10)
        ), new SuccessDate(2024, 12, 11).toJavaDate());


        assertEquals(recurr.calculateNextRecurring(
                new SuccessDate(2024, 12, 3),
                new SuccessDate(2024, 1, 10)
        ), new SuccessDate(2024, 1, 11).toJavaDate());

        assertEquals(recurr.calculateNextRecurring(
                new SuccessDate(2024, 1, 9),
                new SuccessDate(2024, 1, 10)
        ), new SuccessDate(2024, 1, 11).toJavaDate());
    }
}
