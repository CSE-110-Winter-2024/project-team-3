package edu.ucsd.cse110.successorator.ui.goallist;

import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class GoalListObserver implements Observer<List<Goal>> {
    private final GoalListAdapter adapter;
    private final FragmentGoalListBinding view;

    public GoalListObserver(GoalListAdapter adapter, FragmentGoalListBinding view) {
        this.adapter = adapter;
        this.view = view;
    }

    @Override
    public void onChanged(@Nullable List<Goal> goals) {
        if (goals == null) return;

        if (goals.size() == 0) {
            view.noGoalsText.setVisibility(View.VISIBLE);
        } else {
            view.noGoalsText.setVisibility(View.GONE);
        }

        List<Goal> nonCompletedGoals = new ArrayList<>();
        List<Goal> completedGoals = new ArrayList<>();

        for (var goal : goals) {
            if (goal.getCurrCompleted()) {
                completedGoals.add(goal);
            } else {
                nonCompletedGoals.add(goal);
            }
        }

        completedGoals = completedGoals.stream()
                .sorted(Comparator.comparing(Goal::getAssignDate)).collect(Collectors.toList());
        nonCompletedGoals = nonCompletedGoals.stream()
                .sorted(Comparator.comparing(Goal::getAssignDate)).collect(Collectors.toList());


        ArrayList<Goal> newOrderedGoals = new ArrayList<>();
        newOrderedGoals.addAll(nonCompletedGoals);
        newOrderedGoals.addAll(completedGoals);

        adapter.clear();
        adapter.addAll(newOrderedGoals); // remember the mutable copy here!
        adapter.notifyDataSetChanged();
    }
}
