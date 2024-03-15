package edu.ucsd.cse110.successorator.lib.data;

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


public class InMemoryDataSourceTest {
    Goal test1 = new Goal("Do dishes", 1, false, false,
            Date.from(Instant.parse("2024-04-01T15:23:01Z")),
            Date.from(Instant.parse("2024-04-01T15:23:01Z")),
            RepeatType.WEEKLY, FocusType.ALL);

    @Test
    public void emptyInsert() {
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        assertEquals(dataSource.getGoals().size(), 0);

        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);

        dataSource = new InMemoryGoalSource();
        dataSource.putGoals(InMemoryGoalSource.DEFAULT_GOALS);
        assertEquals(dataSource.getGoals().size(), 0);
    }

    @Test
    public void nonEmptyInsert() {
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoals(InMemoryGoalSource.DEFAULT_GOALS);
        assertEquals(dataSource.getGoals().size(), 0);

        var before = dataSource.getGoal(dataSource.getGoals().size() - 1);
        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);

        var after = dataSource.getGoal(dataSource.getGoals().size() - 2);
        assertEquals(before, after);
        assertNotEquals(before, dataSource.getGoal(dataSource.getGoals().size() - 1));
    }
}