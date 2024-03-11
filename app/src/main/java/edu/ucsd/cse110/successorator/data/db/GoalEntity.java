package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.Goal;

@Entity(tableName = "Goal")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "priority")
    public Integer priority;
    @ColumnInfo(name = "completed")
    public Boolean completed;
    @ColumnInfo(name = "completedDate")
    public Date completedDate;
    @ColumnInfo(name = "createdDate")
    public Date createdDate;



    public GoalEntity(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id, @NonNull Boolean completed, Date completedDate, @NonNull Date createdDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.id = id;
        this.completed = completed;
        this.completedDate = completedDate;
        this.createdDate = createdDate;
    }

    @Ignore
    public GoalEntity(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id,@NonNull Date createdDate) {
        this(name, description, priority, id, false, null,createdDate);
    }


    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var goa = new GoalEntity(goal.getName(), goal.getDescription(), goal.getPriority(), goal.getId(), goal.isCompleted(), goal.getCompletedDate(), goal.getCreatedDate());
        goa.id = goal.getId();
        return goa;
    }

    public @NonNull Goal toGoal() {
        return new Goal(name, description, priority, id, completed, completedDate, createdDate);
    }
}