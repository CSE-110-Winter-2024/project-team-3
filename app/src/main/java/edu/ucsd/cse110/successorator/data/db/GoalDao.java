package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

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
        var newGoal = new GoalEntity(goal.name, goal.completed, goal.assignDate, goal.repeatType);

        return Math.toIntExact((Long) insert(newGoal));
    }

    @Query("DELETE FROM Goal WHERE id = :id")
    void delete(int id);

    @Query("UPDATE Goal SET completed = false WHERE id = :id;")
    void setNonCompleted(int id);

    @Query("UPDATE Goal SET completed = true WHERE id = :id;")
    void setCompleted(int id);

}
