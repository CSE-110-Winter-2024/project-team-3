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
    InMemoryDataSource dataSource = InMemoryDataSource.fromDefault();
    GoalRepository goals = new SimpleGoalRepository(dataSource);
    Subject<SuccessDate> successDate = new SimpleSubject<>();
    LocalDate tempDate = LocalDate.now();

    succes


    @Test
    public void dayChange() {
        Day day = new Day(successDate, goals);

        assertEquals(dataSource.getGoals().size(), 6);
    }
}
