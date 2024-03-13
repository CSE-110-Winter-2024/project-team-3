package edu.ucsd.cse110.successorator.lib.data;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;


public class InMemoryDataSourceTest {
    Goal test1 = new Goal("Do dishes", 1, false, Date.from(Instant.parse("2024-03-05T15:23:01Z")), RepeatType.WEEKLY);
    Goal test2 = new Goal("Go to math office hours", 2, false, Date.from(Instant.parse("2024-04-01T15:23:01Z")), RepeatType.ONE_TIME);

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

    @Test
    public void recurringWeekly() {
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);
        dataSource.putGoal(test2);
        assertEquals(dataSource.getGoals().size(), 2);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024,3,6)), false);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024,3,12)), true);
        assertEquals(test1.getAssignDate(), Date.from(Instant.parse("2024-03-05T15:23:01Z")));
        assertEquals(test1.getDescription(), "Weekly on WEDNESDAY");
        assertEquals(test1.getType(), RepeatType.WEEKLY);

    }

    @Test
    public void recurringOneTime() {
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);
        dataSource.putGoal(test2);
        assertEquals(dataSource.getGoals().size(), 2);

        assertEquals(test2.ifDateMatchesRecurring(new SuccessDate(2024,4,1)), true);
        assertEquals(test2.ifDateMatchesRecurring(new SuccessDate(2024,4,8)), false);
        assertEquals(test2.getAssignDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));
        assertEquals(test2.getDescription(), "One-time on Mon Apr 01 08:23:01 PDT 2024");
        assertEquals(test2.getType(), RepeatType.ONE_TIME);
    }

}