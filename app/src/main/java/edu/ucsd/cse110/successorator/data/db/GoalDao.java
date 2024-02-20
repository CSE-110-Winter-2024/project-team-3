package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

@Dao
public interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(GoalEntity goal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<GoalEntity> goals);

    @Query("SELECT * FROM Goal WHERE id = :id")
    GoalEntity find(int id);

    @Query("SELECT * FROM Goal ORDER BY createdDate")
    List<GoalEntity> findAll();

    @Query("SELECT * FROM Goal WHERE id = :id")
    LiveData<GoalEntity> findAsLiveData(int id);

    @Query("SELECT * FROM Goal")
    LiveData<List<GoalEntity>> findAllAsLiveData();

    @Query("SELECT COUNT(*) FROM Goal")
    int count();

    @Transaction
    default int append(GoalEntity goal) {
        var newGoal = new GoalEntity(goal.name, goal.description, goal.priority, goal.id, goal.completed, goal.completedDate, goal.createdDate);

        return Math.toIntExact((Long) insert(newGoal));
    }

    @Query("DELETE FROM Goal WHERE id = :id")
    void delete(int id);

    @Query("UPDATE Goal SET completed = false WHERE id = :id;")
    void setNonCompleted(int id);

    @Query("UPDATE Goal SET completed = true WHERE id = :id;")
    void setCompleted(int id);
}
