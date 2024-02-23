package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;


import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class DayTest {
    InMemoryDataSource dataSource;
    GoalRepository goals;
    Day day;
    Subject<SuccessDate> successDate;
    LocalDate tempDate = LocalDate.now();
    SuccessDate sd = new SuccessDate(tempDate.getYear(), tempDate.getMonth().getValue(), tempDate.getDayOfMonth());

    @Test
    public void dayChange() {
        dataSource = InMemoryDataSource.fromDefault();
        goals = new SimpleGoalRepository(dataSource);
        successDate = new SimpleSubject<>();
        successDate.setValue(sd);

        day = new Day(successDate, goals);
        assertEquals(dataSource.getGoals().size(), 6);
    }
}