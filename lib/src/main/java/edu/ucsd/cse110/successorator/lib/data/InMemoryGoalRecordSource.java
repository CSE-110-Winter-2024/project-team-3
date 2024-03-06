package edu.ucsd.cse110.successorator.lib.data;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRecord;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

/**
 * Class used as a sort of "database" of decks and Goals that exist. This
 * will be replaced with a real database in the future, but can also be used
 * for testing.
 */

public class InMemoryGoalRecordSource {
    private final Map<Integer, GoalRecord> GoalRecords
            = new HashMap<>();
    private final Map<Integer, MutableSubject<GoalRecord>> GoalRecordSubjects
            = new HashMap<>();
    private final MutableSubject<List<GoalRecord>> allGoalRecordsMutableSubject
            = new SimpleSubject<>();

    private int nextid = 0;

    public InMemoryGoalRecordSource() {
    }

    public List<GoalRecord> getGoalRecords() {
        return List.copyOf(GoalRecords.values());
    }

    public GoalRecord getGoal(int id) {
        return GoalRecords.get(id);
    }

    public MutableSubject<GoalRecord> getGoalRecordSubject(int id) {
        if (!GoalRecordSubjects.containsKey(id)) {
            var subject = new SimpleSubject<GoalRecord>();
            subject.setValue(getGoal(id));
            GoalRecordSubjects.put(id, subject);
        }

        return GoalRecordSubjects.get(id);
    }

    public MutableSubject<List<GoalRecord>> getAllGoalRecordsSubject() {
        return allGoalRecordsMutableSubject;
    }

    public void removeGoalRecord(int id) {
        GoalRecords.remove(id);
        // removing an non-existent object is fine
        GoalRecordSubjects.remove(id);

        allGoalRecordsMutableSubject.setValue(getGoalRecords());
    }
    public void putGoalRecord(GoalRecord old_goal) {
        GoalRecord goal = old_goal.withId(nextid);
        nextid++;
        GoalRecords.put(goal.getId(), goal);
        if (GoalRecordSubjects.containsKey(goal.getId())) {
            GoalRecordSubjects.get(goal.getId()).setValue(goal);
        }

        allGoalRecordsMutableSubject.setValue(getGoalRecords());
    }

    public void putGoalRecords(List<GoalRecord> goals) {
        for (var goal : goals) {
            putGoalRecord(goal);
        }
    }

    public final static List<GoalRecord> DEFAULT_RECURRING_GOAL_RECORD = List.of(
            new GoalRecord(
                "Do HW",
                1,
                    Date.from(Instant.parse("2024-03-04T15:23:01Z")),
                new RecurringWeekly()
            ),
            new GoalRecord(
                    "Do HW",
                    2,
                    Date.from(Instant.parse("2024-03-05T15:23:01Z")),
                    new RecurringWeekly()
            )
    );

    public static InMemoryGoalRecordSource fromDefault() {
        var data = new InMemoryGoalRecordSource();
        for (GoalRecord Goal : DEFAULT_RECURRING_GOAL_RECORD) {
            data.putGoalRecord(Goal);
        }
        return data;
    }

}