package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RecurringYearlyTest {

    @Test
    public void ifMatch() {
        RecurringYearly recurr = new RecurringYearly();

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 2, 29),
                new SuccessDate(2025, 3, 1)
        ), true);

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 2, 29),
                new SuccessDate(2025, 2, 28)
        ), false);

        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 3, 5),
                new SuccessDate(2025, 3, 5)
        ), true);


        assertEquals(recurr.ifDateMatchesRecurring(
                new SuccessDate(2024, 12, 31),
                new SuccessDate(2027, 12, 31)
        ), true);
    }

    @Test
    public void calculateNext() {
        RecurringYearly recurr = new RecurringYearly();

//        assertEquals(recurr.calculateNextRecurring(
//                new SuccessDate(2024, 2, 29),
//                new SuccessDate(2025, 2, 3)
//        ), new SuccessDate(2025, 3, 1).toJavaDate());
//
//        assertEquals(recurr.calculateNextRecurring(
//                new SuccessDate(2024, 2, 29),
//                new SuccessDate(2025, 4, 3)
//        ), new SuccessDate(2026, 3, 1).toJavaDate());

    }
}
