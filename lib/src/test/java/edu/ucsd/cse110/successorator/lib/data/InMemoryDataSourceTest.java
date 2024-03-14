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

    Goal test3 = new Goal("Go to gym", 3, false, Date.from(Instant.parse("2024-04-09T15:23:01Z")), RepeatType.DAILY);
    Goal test4 = new Goal("Get haircut", 4, false, Date.from(Instant.parse("2024-04-15T15:23:01Z")), RepeatType.MONTHLY);
    Goal test5 = new Goal("Go to doctors appointment", 5, false, Date.from(Instant.parse("2024-04-18T15:23:01Z")), RepeatType.YEARLY);


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

    @Test
    public void recurringDaily(){
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test3);
        assertEquals(dataSource.getGoals().size(), 1);
        assertEquals(test3.ifDateMatchesRecurring(new SuccessDate(2024, 4, 7)), false);
        assertEquals(test3.ifDateMatchesRecurring(new SuccessDate(2024, 4, 26)), true);
        assertEquals(test3.ifDateMatchesRecurring(new SuccessDate(2024, 4, 9)), true);
        assertEquals(test3.ifDateMatchesRecurring(new SuccessDate(2023, 12, 9)), false);
        assertEquals(test3.getAssignDate(), Date.from(Instant.parse("2024-04-09T15:23:01Z")));
        assertEquals(test3.getDescription(), "Daily starting on Tue Apr 09 08:23:01 PDT 2024");
        assertEquals(test3.getType(), RepeatType.DAILY);
    }

    @Test
    public void recurringMonthly(){
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test4);
        assertEquals(dataSource.getGoals().size(), 1);
        assertEquals(test4.ifDateMatchesRecurring(new SuccessDate(2024, 4, 7)), false);
        assertEquals(test4.ifDateMatchesRecurring(new SuccessDate(2024, 4, 26)), false);
        assertEquals(test4.ifDateMatchesRecurring(new SuccessDate(2024, 5, 15)), true);
        assertEquals(test4.ifDateMatchesRecurring(new SuccessDate(2023, 5, 15)), false);
        assertEquals(test4.getAssignDate(), Date.from(Instant.parse("2024-04-15T15:23:01Z")));
        assertEquals(test4.getDescription(), "Monthly starting on Mon Apr 15 08:23:01 PDT 2024");
        assertEquals(test4.getType(), RepeatType.MONTHLY);
    }

    @Test
    public void recurringYearly(){
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test5);
        assertEquals(dataSource.getGoals().size(), 1);
        assertEquals(test5.ifDateMatchesRecurring(new SuccessDate(2024, 4, 7)), false);
        assertEquals(test5.ifDateMatchesRecurring(new SuccessDate(2024, 4, 18)), true);
        assertEquals(test5.ifDateMatchesRecurring(new SuccessDate(2025, 4, 18)), true);
        assertEquals(test5.ifDateMatchesRecurring(new SuccessDate(2023, 4, 18)), false);
        assertEquals(test5.getAssignDate(), Date.from(Instant.parse("2024-04-18T15:23:01Z")));
        assertEquals(test5.getDescription(), "Yearly starting on Thu Apr 18 08:23:01 PDT 2024");
        assertEquals(test5.getType(), RepeatType.YEARLY);
    }

}