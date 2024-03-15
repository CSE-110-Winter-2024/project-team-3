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

public class RecurringTest {
    Goal test1 = new Goal("Do dishes", 1,
            false, false,
            Date.from(Instant.parse("2024-03-05T15:23:01Z")),
            Date.from(Instant.parse("2024-03-12T15:23:01Z")),
            RepeatType.WEEKLY, FocusType.ALL);

    Goal test2 = new Goal("Go to math office hours", 2,
            false, false,
            Date.from(Instant.parse("2024-04-01T15:23:01Z")),
            null,
            RepeatType.ONE_TIME, FocusType.SCHOOL);

    Goal test3 = new Goal("Go to gym", 3,
            false, false,
            Date.from(Instant.parse("2024-04-09T15:23:01Z")),
            Date.from(Instant.parse("2024-04-09T15:23:01Z")),
            RepeatType.DAILY, FocusType.ERRANDS);

    Goal test4 = new Goal("Get haircut", 4,
            false, false,
            Date.from(Instant.parse("2024-04-15T15:23:01Z")),
            Date.from(Instant.parse("2024-05-15T15:23:01Z")),
            RepeatType.MONTHLY, FocusType.ERRANDS);

    Goal test5 = new Goal("Go to doctors appointment", 5, false, false,
            Date.from(Instant.parse("2024-04-18T15:23:01Z")),
            Date.from(Instant.parse("2024-04-18T15:23:01Z")),
            RepeatType.YEARLY, FocusType.ERRANDS);

        @Test
    public void recurringWeekly() {
        InMemoryGoalSource dataSource = new InMemoryGoalSource();
        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);
        dataSource.putGoal(test2);
        assertEquals(dataSource.getGoals().size(), 2);

        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024,3,6)), false);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024,3,12)), true);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024, 3, 19)), true);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024, 4, 9)), true);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2025,1, 7)), true);

        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2024, 4, 8)), true);
        assertEquals(test1.ifDateMatchesRecurring(new SuccessDate(2025, 1, 6)), true);

        assertEquals(test1.getAssignDate(), Date.from(Instant.parse("2024-03-05T15:23:01Z")));
        assertEquals(test1.getDescription(), "weekly on Tue");
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
        assertEquals(test2.getDescription(), "");
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
        assertEquals(test3.getDescription(), "daily");
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

        assertEquals(test4.ifDateMatchesRecurring(new SuccessDate(2024, 5, 16)), true);

        assertEquals(test4.getAssignDate(), Date.from(Instant.parse("2024-04-15T15:23:01Z")));
        assertEquals(test4.getDescription(), "monthly on every 3th Monday");
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
        assertEquals(test5.getDescription(), "yearly on Apr 18");
        assertEquals(test5.getType(), RepeatType.YEARLY);
    }


}
