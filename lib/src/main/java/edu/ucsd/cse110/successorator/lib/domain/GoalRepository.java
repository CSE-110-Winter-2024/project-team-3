package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;


public interface GoalRepository {
    Subject<Goal> find(int id);

    Subject<List<Goal>> findAll();
    Subject<List<Goal>> findAll(SuccessDate successDate);
    Subject<List<Goal>> findPending();

    public Subject<List<Goal>> findOneTime();

    Subject<List<Goal>> findRecurring();

    void save(Goal goal);

    void save(List<Goal> goals);

    void remove(int id);

    void append(Goal goal);


    void setCompleted(int id);

    void setNextCompleted(int id);

    void setNextNonCompleted(int id);

    void setNonCompleted(int id);
}
