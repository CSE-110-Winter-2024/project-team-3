package edu.ucsd.cse110.successorator.lib.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

/**
 * Class used as a sort of "database" of decks and Goals that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */

public class InMemoryDataSource {
    private final Map<Integer, Goal> Goals
            = new HashMap<>();
    private final Map<Integer, Subject<Goal>> GoalSubjects
            = new HashMap<>();
    private final Subject<List<Goal>> allGoalsSubject
            = new SimpleSubject<>();

    private int nextid = 0;

    public InMemoryDataSource() {
    }

    public List<Goal> getGoals() {
        return List.copyOf(Goals.values());
    }

    public Goal getGoal(int id) {
        return Goals.get(id);
    }

    public Subject<Goal> getGoalSubject(int id) {
        if (!GoalSubjects.containsKey(id)) {
            var subject = new SimpleSubject<Goal>();
            subject.setValue(getGoal(id));
            GoalSubjects.put(id, subject);
        }

        return GoalSubjects.get(id);
    }

    public Subject<List<Goal>> getAllGoalsSubject() {
        return allGoalsSubject;
    }

    // completeGoal
    public void completeGoal(int id, Date date) {
        Goal modifiedGoal = getGoal(id).withComplete(true, date);
        Goals.put(modifiedGoal.getId(), modifiedGoal);
        if (GoalSubjects.containsKey(modifiedGoal.getId())) {
            GoalSubjects.get(modifiedGoal.getId()).setValue(modifiedGoal);
        }

        allGoalsSubject.setValue(getGoals());
    }

    public void removeGoal(int id) {
        Goals.remove(id);
        // removing an non-existent object is fine
        GoalSubjects.remove(id);

        allGoalsSubject.setValue(getGoals());
    }
    public void putGoal(Goal old_goal) {
        Goal goal = old_goal.withId(nextid);
        nextid++;
        Goals.put(goal.getId(), goal);
        if (GoalSubjects.containsKey(goal.getId())) {
            GoalSubjects.get(goal.getId()).setValue(goal);
        }

        allGoalsSubject.setValue(getGoals());
    }

    public void putGoals(List<Goal> goals) {
        for (var goal : goals) {
            putGoal(goal);
        }
    }

    public int getMaxPriority() {
        return this.Goals.values().stream()
                .map(Goal::getPriority)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
    }

    public final static List<Goal> DEFAULT_GOALS = List.of(
            new Goal("SRP", "Single Responsibility Principle", 10, 1, new Date()),
            new Goal("OCP", "Open-Closed Principle", 10, 2, new Date()),
            new Goal("LSP", "Liskov Substitution Principle", 10, 3, new Date()),
            new Goal("ISP", "Interface Segregation Principle", 10, 4, new Date()),
            new Goal("DIP", "Dependency Inversion Principle", 10, 5, new Date()),
            new Goal("LKP", "Least Knowledge Principle (Law of Demeter)", 10, 6, new Date())
    );

    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        for (Goal Goal : DEFAULT_GOALS) {
            data.putGoal(Goal);
        }
        return data;
    }

}