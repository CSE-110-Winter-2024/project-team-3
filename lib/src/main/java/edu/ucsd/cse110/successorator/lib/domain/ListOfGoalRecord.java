package edu.ucsd.cse110.successorator.lib.domain;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface ListOfGoalRecord {
    Subject<GoalRecord> find(int id);

    Subject<List<GoalRecord>> findAll();

    void save(GoalRecord goal);

    void save(List<GoalRecord> goals);

    void remove(int id);


    Day createDay(SuccessDate date, Day previosuDay);
    Day createDay(SuccessDate date);

}
