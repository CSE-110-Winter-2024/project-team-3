package edu.ucsd.cse110.successorator.data.db;

import static androidx.lifecycle.Transformations.map;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;
import edu.ucsd.cse110.successorator.util.LiveDataMutableSubjectAdapter;
import edu.ucsd.cse110.successorator.util.MutableLiveDataSubjectAdapter;

public class RoomGoalRepository implements GoalRepository {
    private final GoalDao goalDao;

    public RoomGoalRepository(GoalDao goalDao) {
        this.goalDao =goalDao;
    }

    @Override
    public Subject<Goal> find(int id) {
        LiveData<GoalEntity> entityLiveData = goalDao. findAsLiveData(id);
        LiveData<Goal> goalLiveData = map(entityLiveData, GoalEntity::toGoal);

        return new LiveDataMutableSubjectAdapter<>(goalLiveData);
    }

    @Override
    public Subject<List<Goal>> findAll() {
        var entitiesLiveData = goalDao.findAllAsLiveData();
        var goalsLiveData = map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });

        Log.i("roomGoalRepository", (goalsLiveData.getValue() == null) ? "is null" : "not null");

        return new LiveDataMutableSubjectAdapter<>(goalsLiveData);
    }


    @Override
    public Subject<List<Goal>> findAll(SuccessDate successDate) {
        Long startOfDay = successDate.toJavaDate().getTime();
        var entitiesLiveData = goalDao.findAllAsLiveData(startOfDay, startOfDay + (24*60*60*1000));
        var goalsLiveData = map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });

        Log.i("roomGoalRepository", (goalsLiveData.getValue() == null) ? "is null" : "not null");

        return new LiveDataMutableSubjectAdapter<>(goalsLiveData);
    }


    @Override
    public Subject<List<Goal>> findOneTime() {
        var entitiesLiveData = goalDao.findAllOneTime();
        var goalsLiveData = map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });

        Log.i("roomGoalRepository", (goalsLiveData.getValue() == null) ? "is null" : "not null");

        return new LiveDataMutableSubjectAdapter<>(goalsLiveData);
    }

    @Override
    public Subject<List<Goal>> findPending() {
        var entitiesLiveData = goalDao.findAllPending();
        var goalsLiveData = map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });

        Log.i("roomGoalRepository", (goalsLiveData.getValue() == null) ? "is null" : "not null");

        return new LiveDataMutableSubjectAdapter<>(goalsLiveData);
    }

    @Override
    public Subject<List<Goal>> findRecurring() {
        var entitiesLiveData = goalDao.findAllRecurring();
        var goalsLiveData = map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(GoalEntity::toGoal)
                    .collect(Collectors.toList());
        });

        Log.i("roomGoalRepository", (goalsLiveData.getValue() == null) ? "is null" : "not null");

        return new LiveDataMutableSubjectAdapter<>(goalsLiveData);
    }

    @Override
    public void save(Goal goal) {
        goalDao.insert(GoalEntity.fromGoal(goal));
    }

    @Override
    public void save(List<Goal> goals) {
        var entities = goals. stream()
                .map(GoalEntity::fromGoal)
                .collect(Collectors. toList());

        goalDao.insert(entities);
    }

    @Override
    public void remove(int id) {
        goalDao.delete(id);
    }

    @Override
    public void append(Goal goal) {
        goalDao.append(GoalEntity.fromGoal(goal));
    }

    @Override
    public void setCompleted(int id) {
        goalDao.setCompleted(id);
    }

    @Override
    public void setNonCompleted(int id) {
        goalDao.setNonCompleted(id);
    }
}
