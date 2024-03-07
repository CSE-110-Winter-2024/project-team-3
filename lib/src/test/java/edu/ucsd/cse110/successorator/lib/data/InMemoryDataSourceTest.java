package edu.ucsd.cse110.successorator.lib.data;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRecord;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;


public class InMemoryDataSourceTest {
    GoalRecord goalRecordTest1 = new GoalRecord("Do dishes", 1, Date.from(Instant.parse("2024-03-05T15:23:01Z")), new RecurringWeekly());
    Goal test1 = goalRecordTest1.toGoal();
    GoalRecord goalRecordTest2 = new GoalRecord("Go to math office hours", 2,  Date.from(Instant.parse("2024-04-01T15:23:01Z")), new RecurringOneTime());
    Goal test2 = goalRecordTest2.toGoal();

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
        assertEquals(goalRecordTest1.ifDateMatchesRecurring(Date.from(Instant.parse("2024-03-06T15:23:01Z"))), false);
        assertEquals(goalRecordTest1.ifDateMatchesRecurring(Date.from(Instant.parse("2024-03-12T15:23:01Z"))), true);
        assertEquals(goalRecordTest1.getStartDate(), Date.from(Instant.parse("2024-03-05T15:23:01Z")));
        assertEquals(goalRecordTest1.getDescription(), "Weekly on WEDNESDAY");
        assertEquals(goalRecordTest1.getType(), RecurringType.RepeatType.WEEKLY);

        assertEquals(goalRecordTest2.ifDateMatchesRecurring(Date.from(Instant.parse("2024-04-01T15:23:01Z"))), true);
        assertEquals(goalRecordTest2.ifDateMatchesRecurring(Date.from(Instant.parse("2024-04-08T15:25:01Z"))), false);
        assertEquals(goalRecordTest2.getStartDate(), Date.from(Instant.parse("2024-04-01T15:23:01Z")));
        assertEquals(goalRecordTest2.getDescription(), "One-time on Mon Apr 01 08:23:01 PDT 2024");
        assertEquals(goalRecordTest2.getType(), RecurringType.RepeatType.ONE_TIME);

    }
}