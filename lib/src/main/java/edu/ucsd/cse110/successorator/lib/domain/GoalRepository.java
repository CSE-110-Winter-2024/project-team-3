package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;


public interface GoalRepository {
    MutableSubject<Goal> find(int id);

    MutableSubject<List<Goal>> findAll();

    void save(Goal goal);

    void save(List<Goal> goals);

    void remove(int id);

    void append(Goal goal);


    void setCompleted(int id);
    void setNonCompleted(int id);
}
