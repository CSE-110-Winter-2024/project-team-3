package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleGoalRepository implements GoalRepository {
    private final InMemoryGoalSource dataSource;

    public SimpleGoalRepository(InMemoryGoalSource dataSource) {
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
    public Subject<List<Goal>> findAll(SuccessDate successDate) {
        return new SimpleSubject<>();
    }

    @Override
    public Subject<List<Goal>> findPending() {
        return null;
    }
    @Override
    public Subject<List<Goal>> findOneTime() {
        return null;
    }

    @Override
    public Subject<List<Goal>> findRecurring() {
        return null;
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
        dataSource.putGoal(goal);
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
