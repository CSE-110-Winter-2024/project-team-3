package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleListOfGoalRecord implements ListOfGoalRecord {
    InMemoryDataSource inMemoryDataSource;

    public SimpleListOfGoalRecord(InMemoryDataSource inMemoryDataSource) {
        this.inMemoryDataSource = inMemoryDataSource;
    }

    @Override
    public Subject<GoalRecord> find(int id) {
        return null;
    }

    @Override
    public Subject<List<GoalRecord>> findAll() {
        return null;
    }

    @Override
    public void save(GoalRecord goal) {

    }

    @Override
    public void save(List<GoalRecord> goals) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void append(GoalRecord goal) {

    }

    @Override
    public Day createDay(SuccessDate date, Day previosuDay) {
        return null;
    }

    @Override
    public Day createDay(SuccessDate date) {
        return new Day(date, new SimpleGoalRepository(InMemoryDataSource.fromDefault()));
    }

    @Override
    public void setCompleted(int id) {

    }

    @Override
    public void setNonCompleted(int id) {

    }
}
