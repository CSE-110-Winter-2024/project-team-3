package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.FocusType;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;

@Entity(tableName = "Goal")
public class GoalEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "currCompleted")
    public Boolean currCompleted;
    @ColumnInfo(name = "nextCompleted")
    public Boolean nextCompleted;
    @ColumnInfo(name = "assignDate")
    public Date assignDate;
    @ColumnInfo(name = "currIterDate")
    public Date currIterDate;
    @ColumnInfo(name = "recurringType")
    public RepeatType repeatType;
    @ColumnInfo(name = "focusType")
    public FocusType focusType;


    public GoalEntity(@NonNull String name, @NonNull Boolean currCompleted, @NonNull Boolean nextCompleted, Date assignDate, Date currIterDate, @NonNull RepeatType repeatType, FocusType focusType) {
        this.name = name;
        this.currCompleted = currCompleted;
        this.nextCompleted = nextCompleted;
        this.assignDate = assignDate;
        this.currIterDate = currIterDate;
        this.repeatType = repeatType;
        this.focusType = focusType;
    }


    public static GoalEntity fromGoal(@NonNull Goal goal) {
        var goa = new GoalEntity(goal.getName(), goal.getCurrCompleted(), goal.getNextCompleted(), goal.getAssignDate(), goal.getCurrIterDate(), goal.getType(), goal.get_focus());
        goa.id = goal.getId();
        return goa;
    }

    public @NonNull Goal toGoal() {
        return new Goal(name, id, currCompleted, nextCompleted, assignDate, currIterDate, repeatType, focusType);
    }
}