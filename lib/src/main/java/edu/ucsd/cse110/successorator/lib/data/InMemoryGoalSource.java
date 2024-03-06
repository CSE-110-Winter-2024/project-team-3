package edu.ucsd.cse110.successorator.lib.data;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRecord;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

/**
 * Class used as a sort of "database" of decks and Goals that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */

public class InMemoryGoalSource {
    private final Map<Integer, Goal> Goals
            = new HashMap<>();
    private final Map<Integer, MutableSubject<Goal>> GoalSubjects
            = new HashMap<>();
    private final MutableSubject<List<Goal>> allGoalsMutableSubject
            = new SimpleSubject<>();

    private int nextid = 0;

    public InMemoryGoalSource() {
    }

    public List<Goal> getGoals() {
        return List.copyOf(Goals.values());
    }

    public Goal getGoal(int id) {
        return Goals.get(id);
    }

    public MutableSubject<Goal> getGoalSubject(int id) {
        if (!GoalSubjects.containsKey(id)) {
            var subject = new SimpleSubject<Goal>();
            subject.setValue(getGoal(id));
            GoalSubjects.put(id, subject);
        }

        return GoalSubjects.get(id);
    }

    public MutableSubject<List<Goal>> getAllGoalsSubject() {
        return allGoalsMutableSubject;
    }

    // completeGoal
    public void completeGoal(int id) {
        Goal modifiedGoal = getGoal(id).withComplete(true);
        Goals.put(modifiedGoal.getId(), modifiedGoal);
        if (GoalSubjects.containsKey(modifiedGoal.getId())) {
            GoalSubjects.get(modifiedGoal.getId()).setValue(modifiedGoal);
        }

        allGoalsMutableSubject.setValue(getGoals());
    }

    // completeGoal
    public void unCompleteGoal(int id) {
        Goal modifiedGoal = getGoal(id).withComplete(false);
        Goals.put(modifiedGoal.getId(), modifiedGoal);
        if (GoalSubjects.containsKey(modifiedGoal.getId())) {
            GoalSubjects.get(modifiedGoal.getId()).setValue(modifiedGoal);
        }

        allGoalsMutableSubject.setValue(getGoals());
    }

    public void removeGoal(int id) {
        Goals.remove(id);
        // removing an non-existent object is fine
        GoalSubjects.remove(id);

        allGoalsMutableSubject.setValue(getGoals());
    }
    public void putGoal(Goal old_goal) {
        Goal goal = old_goal.withId(nextid);
        nextid++;
        Goals.put(goal.getId(), goal);
        if (GoalSubjects.containsKey(goal.getId())) {
            GoalSubjects.get(goal.getId()).setValue(goal);
        }

        allGoalsMutableSubject.setValue(getGoals());
    }

    public void putGoals(List<Goal> goals) {
        for (var goal : goals) {
            putGoal(goal);
        }
    }

    public final static List<Goal> DEFAULT_GOALS = List.of(
    );

    public static InMemoryGoalSource fromDefault() {
        var data = new InMemoryGoalSource();
        for (Goal Goal : DEFAULT_GOALS) {
            data.putGoal(Goal);
        }
        return data;
    }

}