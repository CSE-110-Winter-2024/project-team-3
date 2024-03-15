package edu.ucsd.cse110.successorator.app;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.domain.FocusType;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

public class BDDs {
    InMemoryGoalSource datasource = new InMemoryGoalSource();

    @Test
    public void US8a() {
        Goal weeklyGoal = new Goal("Clean the bathroom", 1,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-08T15:23:01Z")),
                RepeatType.WEEKLY, FocusType.ALL);

        datasource.putGoal(weeklyGoal);

        // To show that test is created today
        assertEquals(weeklyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));

        // Test reappears exactly one week from today
        assertEquals(weeklyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 4, 8)), true);
        // On date that is not on day of week, evaluates false
        assertNotEquals(weeklyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 4, 10)), false);
    }

    @Test
    public void US8b() {
        // Given today is April 1, 2024
        Goal weeklyGoal = new Goal("Clean the bathroom", 1,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-08T15:23:01Z")),
                RepeatType.WEEKLY, FocusType.ALL);

        Goal monthlyGoal = new Goal("Pay bills", 2,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-05-01T15:23:01Z")),
                RepeatType.MONTHLY, FocusType.ALL);

        Goal yearlyGoal = new Goal("Celebrate birthday", 3,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.YEARLY, FocusType.ALL);

        datasource.putGoal(weeklyGoal);
        datasource.putGoal(monthlyGoal);
        datasource.putGoal(yearlyGoal);

        // weekly
        assertEquals(weeklyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));
        assertEquals(weeklyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 4, 8)), true);
        assertNotEquals(weeklyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 4, 10)), false);

        // monthly
        assertEquals(monthlyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));
        assertEquals(monthlyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 5, 1)), false);
        assertNotEquals(monthlyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 5, 3)), false);
        assertEquals(monthlyGoal.ifDateMatchesRecurring(new SuccessDate(2025, 5, 1)), true);
        assertNotEquals(monthlyGoal.ifDateMatchesRecurring(new SuccessDate(2025, 5, 3)), false);

        // yearly
        assertEquals(yearlyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));
        assertEquals(yearlyGoal.ifDateMatchesRecurring(new SuccessDate(2025, 4, 1)),true);
        assertNotEquals(yearlyGoal.ifDateMatchesRecurring(new SuccessDate(2024, 4, 2)), false);
    }

    @Test
    public void US9() {

    }

    @Test
    public void US10() {

    }

    @Test
    public void US11() {

    }
    @Test
    public void US12() {
    }
    @Test
    public void US13() {

    }
}
