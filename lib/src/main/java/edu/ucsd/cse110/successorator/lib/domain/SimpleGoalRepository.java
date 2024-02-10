package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleGoalRepository implements GoalRepository{
    private final InMemoryDataSource dataSource;

    public SimpleGoalRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Subject<Goal> find(int id) {
        return dataSource.getGoalSubject(id);
    }

    @Override
    public Subject<List<Goal>> findAll() {
        return dataSource.getAllGoalsSubject();
    }

    @Override
    public void save(Goal goal) {
        dataSource.putGoal(goal);
    }

    @Override
    public void save(List<Goal> goals) {
        dataSource.putGoals(goals);
    }

    @Override
    public void remove(int id) {
        dataSource.removeGoal(id);
    }


    @Override
    public void append(Goal goal) {
        dataSource.putGoal(
                goal.withPriority(dataSource.getMaxPriority() + 1)
        );
    }

    @Override
    public void complete(int id, Date completeDate) {
        dataSource.completeGoal(id, completeDate);
    }

}
