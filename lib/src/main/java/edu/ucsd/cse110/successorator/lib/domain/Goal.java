package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Goal {
    private final @NonNull String name;
    private final @NonNull String description;
    private final @NonNull Integer priority;
    private final @NonNull Integer id;
    private final @NonNull Boolean completed;
    private final Date completedDate;
    private final @NonNull Date createdDate;



    public Goal(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id, @NonNull Boolean completed, Date completedDate, @NonNull Date createdDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.id = id;
        this.completed = completed;
        this.completedDate = completedDate;
        this.createdDate = createdDate;
    }

    public Goal(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id,@NonNull Date createdDate) {
        this(name, description, priority, id, false, null,createdDate);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public Integer getPriority() {
        return priority;
    }

    @NonNull
    public Date getCreatedDate(){
        return createdDate;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Date getCompleted() {
        return this.completedDate;
    }

    @NonNull
    public boolean isCompleted(){
        return this.completed;
    }


    @NonNull
    public Goal withPriority(int newPriority) {
        return new Goal(name, description, newPriority, id, completed, completedDate, createdDate);
    }
    @NonNull
    public Goal withComplete(boolean newComplete) {
        return new Goal(name, description, priority, id, newComplete, null, createdDate);
    }

    @NonNull
    public Goal withId(int id) {
        return new Goal(name, description, priority, id, completed, completedDate, createdDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return Objects.equals(name, goal.name) && Objects.equals(description, goal.description) && Objects.equals(priority, goal.priority) && Objects.equals(id, goal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, priority, id);
    }

    public Date getCompletedDate() {
        return completedDate;
    }
}
