package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalRecordSource;
import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleListOfGoalRecord implements ListOfGoalRecord {
    InMemoryGoalRecordSource dataSource;

    public SimpleListOfGoalRecord(InMemoryGoalRecordSource inMemoryDataSource) {
        this.dataSource = inMemoryDataSource;
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
        dataSource.putGoalRecord(goal);
    }

    @Override
    public void save(List<GoalRecord> goals) {
        dataSource.putGoalRecords(goals);
    }

    @Override
    public void remove(int id) {
        dataSource.removeGoalRecord(id);
    }

    @Override
    public Day createDay(SuccessDate date, Day previosuDay) {
        return null;
    }

    @Override
    public Day createDay(SuccessDate date) {
        return new Day(date, new SimpleListOfGoals(InMemoryGoalSource.fromDefault()));
    }

}
