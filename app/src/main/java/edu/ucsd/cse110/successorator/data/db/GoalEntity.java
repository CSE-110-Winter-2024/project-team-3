package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;

@Entity(tableName = "Goal")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "completed")
    public Boolean completed;
    @ColumnInfo(name = "assignDate")
    public Date assignDate;
    @ColumnInfo(name = "currDate")
    public Date currDate;
    @ColumnInfo(name = "recurringType")
    public RepeatType repeatType;
    @ColumnInfo(name = "focusType")
    public String focusType;


    public GoalEntity(@NonNull String name, @NonNull Boolean completed, Date assignDate, Date currIter, @NonNull RepeatType repeatType, String focusType) {
        this.name = name;
        this.completed = completed;
        this.assignDate = assignDate;
        this.repeatType = repeatType;
        this.focusType = focusType;
        this.currDate = currIter;
    }


    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var goa = new GoalEntity(goal.getName(), goal.isCompleted(), goal.getAssignDate(), goal.getCurrIter(), goal.getType(), goal.get_focus());
        goa.id = goal.getId();
        return goa;
    }

    public @NonNull Goal toGoal() {
        return new Goal(name, id, completed, assignDate, currDate, repeatType, focusType);
    }
}