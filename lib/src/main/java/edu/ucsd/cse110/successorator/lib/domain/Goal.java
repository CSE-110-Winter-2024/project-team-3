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



    public Goal(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id, @NonNull Boolean completed, Date completedDate) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.id = id;
        this.completed = completed;
        this.completedDate = completedDate;
    }

    public Goal(@NonNull String name, @NonNull String description, @NonNull Integer priority, @NonNull Integer id) {
        this(name, description, priority, id, false, null);
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
    public Integer getId() {
        return id;
    }


    @NonNull
    public Goal withPriority(int newPriority) {
        return new Goal(name, description, newPriority, id, completed, completedDate);
    }
    @NonNull
    public Goal withComplete(boolean newComplete, Date newCompleteDate) {
        return new Goal(name, description, priority, id, newComplete, newCompleteDate);
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
}
