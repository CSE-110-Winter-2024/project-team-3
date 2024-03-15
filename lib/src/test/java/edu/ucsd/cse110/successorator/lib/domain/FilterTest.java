package edu.ucsd.cse110.successorator.lib.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

/**
 * Tests for SuccessDate Class type
 */
public class FilterTest {
    @Test
    public void filter_goals_test() {

        Goal homeGoal = new Goal("Home Goal Title", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.HOME);

        Goal workGoal = new Goal("Work Goal Title", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.WORK);

        List<Goal> goals = List.of(homeGoal, workGoal);

        List<Goal> filteredGoals = Filter.filter_goals(goals, FocusType.WORK);
        assertEquals(filteredGoals, List.of(workGoal));
        filteredGoals = Filter.filter_goals(goals, FocusType.HOME);
        assertEquals(filteredGoals, List.of(homeGoal));
        filteredGoals = Filter.filter_goals(goals, FocusType.ERRANDS);
        assertEquals(filteredGoals, List.of());
    }


    @Test
    public void filter_empty() {


        List<Goal> goals = List.of();

        List<Goal> filteredGoals = Filter.filter_goals(goals, FocusType.WORK);
        assertEquals(filteredGoals, List.of());
        filteredGoals = Filter.filter_goals(goals, FocusType.HOME);
        assertEquals(filteredGoals, List.of());
        filteredGoals = Filter.filter_goals(goals, FocusType.ERRANDS);
        assertEquals(filteredGoals, List.of());
    }


    @Test
    public void filter_all() {

        Goal homeGoal = new Goal("Home Goal Title", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.HOME);

        Goal workGoal = new Goal("Work Goal Title", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.WORK);

        List<Goal> goals = List.of(homeGoal, workGoal);

        List<Goal> filteredGoals = Filter.filter_goals(goals, FocusType.ALL);
        assertEquals(filteredGoals, List.of(homeGoal, workGoal));
    }
}
