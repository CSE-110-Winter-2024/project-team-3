package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> goals);


//    @Query("SELECT * FROM Goal WHERE :date == :date")
    @Query("SELECT * FROM Goal WHERE (assignDate BETWEEN :startDate AND :endDate) OR (recurringType = 'DAILY') OR (recurringType = 'WEEKLY' AND strftime('%w', DATETIME(ROUND(assignDate/1000), 'unixepoch')) = strftime('%w', DATETIME(ROUND(:startDate/1000), 'unixepoch'))) ")
    LiveData<List<GoalEntity>> findAllAsLiveData(Long startDate, Long endDate);


    @Query("SELECT * FROM Goal WHERE assignDate IS NULL")
    LiveData<List<GoalEntity>> findAllPending();


    @Query("SELECT * FROM Goal WHERE NOT (recurringType = 'ONE_TIME')")
    LiveData<List<GoalEntity>> findAllRecurring();
    @Query("SELECT * FROM Goal WHERE (recurringType = 'ONE_TIME')")
    LiveData<List<GoalEntity>> findAllOneTime();

    @Query("SELECT * FROM Goal WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM Goal")
    LiveData<List<GoalEntity>> findAllAsLiveData();

    @Query("SELECT COUNT(*) FROM Goal")
    int count();

    @Transaction
    default int append(GoalEntity goal) {
        var newGoal = new GoalEntity(goal.name, goal.currCompleted, goal.nextCompleted, goal.assignDate, goal.currIterDate, goal.repeatType, goal.focusType);

        return Math.toIntExact((Long) insert(newGoal));
    }

    @Query("DELETE FROM Goal WHERE id = :id")
    void delete(int id);

    @Query("UPDATE Goal SET currCompleted = false WHERE id = :id;")
    void setCurrNonCompleted(int id);

    @Query("UPDATE Goal SET currCompleted = true WHERE id = :id;")
    void setCurrCompleted(int id);

    @Query("UPDATE Goal SET nextCompleted = true WHERE id = :id;")
    void setNextCompleted(int id);
    @Query("UPDATE Goal SET nextCompleted = false WHERE id = :id;")
    void setNextNonCompleted(int id);
}
