package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class SimpleGoalRepository implements GoalRepository{
    private final InMemoryDataSource dataSource;

    public SimpleGoalRepository(InMemoryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public MutableSubject<Goal> find(int id) {
        return dataSource.getGoalSubject(id);
    }

    @Override
    public MutableSubject<List<Goal>> findAll() {
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
    public void setCompleted(int id) {
        dataSource.completeGoal(id);
    }

    @Override
    public void setNonCompleted(int id) {
        dataSource.unCompleteGoal(id);
    }


}
