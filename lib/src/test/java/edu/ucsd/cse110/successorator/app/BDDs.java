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

        assertEquals(weeklyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-08T15:23:01Z")));
        assertEquals(weeklyGoal.getAssignDate(), Date.from(Instant.parse("2024-04-08T15:23:01Z")));
    }

    @Test
    public void US8b() {

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
