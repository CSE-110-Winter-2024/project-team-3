package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;


import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class DayTest {
    InMemoryGoalSource dataSource;
    ListOfGoals goals;
    Day day;
    SimpleSubject<SuccessDate> successDate;
    LocalDate tempDate = LocalDate.now();
    SuccessDate sd = new SuccessDate(tempDate.getYear(), tempDate.getMonth().getValue(), tempDate.getDayOfMonth());

    @Test
    public void dayChange() {
        dataSource = InMemoryGoalSource.fromDefault();
        goals = new SimpleListOfGoals(dataSource);
        successDate = new SimpleSubject<>();
        successDate.setValue(sd);


        day = new Day(successDate, goals);
        assertEquals(dataSource.getGoals().size(), 6);
    }
}
