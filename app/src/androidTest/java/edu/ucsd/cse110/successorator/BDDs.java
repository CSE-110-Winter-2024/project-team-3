package edu.ucsd.cse110.successorator.app;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.*;

import androidx.room.Room;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.DisplayGoalType;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.domain.FocusType;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

public class BDDs {

    public BDDs() {
    }

    @Test
    public void US8a() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

        Goal weeklyGoal = new Goal("Clean the bathroom", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.WEEKLY, FocusType.HOME);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.putGoal(weeklyGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);

        activityModel.setCompleted(activityModel.getDisplayGoals().getValue().get(0).getId());

        activityModel.mockAdvanceDay();

        List<Goal> temp = activityModel.getDisplayGoals().getValue();
        System.out.println(activityModel.getDisplayGoals().getValue());
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);


        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);
    }

    @Test
    public void US8b() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

        Goal dailyGoal = new Goal("Clean the bathroom", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.DAILY, FocusType.HOME);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.putGoal(dailyGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);

        activityModel.setCompleted(activityModel.getDisplayGoals().getValue().get(0).getId());

        activityModel.mockAdvanceDay();

        List<Goal> temp = activityModel.getDisplayGoals().getValue();
        System.out.println(activityModel.getDisplayGoals().getValue());
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);


        activityModel.setCompleted(activityModel.getDisplayGoals().getValue().get(0).getId());
        activityModel.setDisplayGoalType(DisplayGoalType.TOMORROW);
        activityModel.setCompleted(activityModel.getDisplayGoals().getValue().get(0).getId());

        activityModel.mockAdvanceDay();

        activityModel.setDisplayGoalType(DisplayGoalType.TODAY);
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();
        activityModel.mockAdvanceDay();


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);
    }

    @Test
    public void US9() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

        Goal weeklyGoal = new Goal("Clean the bathroom", null,
                false, false,
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                Date.from(Instant.parse("2024-04-01T15:23:01Z")),
                RepeatType.WEEKLY, FocusType.HOME);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.putGoal(weeklyGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);

        activityModel.setCompleted(activityModel.getDisplayGoals().getValue().get(0).getId());

        activityModel.mockAdvanceDay();

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.setDisplayGoalType(DisplayGoalType.RECURRING);
        //Recurrign screen shows goals
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);
    }

    @Test
    public void US10() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

        Goal tmrGoal = new Goal("Clean the bathroom", null,
                false, false,
                Date.from(Instant.parse("2024-04-02T15:23:01Z")),
                Date.from(Instant.parse("2024-04-02T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.HOME);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.putGoal(tmrGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.setDisplayGoalType(DisplayGoalType.TOMORROW);
        // Tomorrow goals shows up tomorrow
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);
    }

    @Test
    public void US11() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

        Goal weeklyGoal = new Goal("Clean the bathroom", null,
                false, false,
                Date.from(Instant.parse("2024-04-02T15:23:01Z")),
                Date.from(Instant.parse("2024-04-02T15:23:01Z")),
                RepeatType.ONE_TIME, FocusType.HOME);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.putGoal(weeklyGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        activityModel.setDisplayGoalType(DisplayGoalType.TOMORROW);
        //Recurrign screen shows goals
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);

    }
    @Test
    public void US12() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

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


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        // inserted in not the right order
        activityModel.putGoal(workGoal);
        activityModel.putGoal(homeGoal);

        // goals needs to be sorted, so the first goal is home goal
        assertEquals(activityModel.getDisplayGoals().getValue().get(0).getName(), "Home Goal Title");
        // second is work goal
        assertEquals(activityModel.getDisplayGoals().getValue().get(1).getName(), "Work Goal Title");
    }
    @Test
    public void US13() {
        InMemoryGoalSource datasource = InMemoryGoalSource.fromDefault();
        GoalRepository goalRepository = new SimpleGoalRepository(datasource);
        MainViewModel activityModel = new MainViewModel(goalRepository);
        activityModel.setTodayDate(new SuccessDate(2024, 4, 1));

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


        assertEquals(activityModel.getDisplayGoals().getValue().size(), 0);

        // inserted in not the right order
        activityModel.putGoal(workGoal);
        activityModel.putGoal(homeGoal);

        assertEquals(activityModel.getDisplayGoals().getValue().size(), 2);

        activityModel.setFocusType(FocusType.WORK);

        // only work goal should be displaying
        assertEquals(activityModel.getDisplayGoals().getValue().size(), 1);

        // second is work goal
        assertEquals(activityModel.getDisplayGoals().getValue().get(0).getName(), "Work Goal Title");

    }
}
