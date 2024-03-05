package edu.ucsd.cse110.successorator.ui.goallist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalListAdapter extends ArrayAdapter<Goal> {
//    Consumer<Integer> onDeleteClick;

    private MainViewModel activityModel;

    public GoalListAdapter(Context context, List<Goal> goals, MainViewModel activityModel) {
        // This sets a bunch of stuff internally, which we can access
        // with getContext() and getItem() for example.
        //
        // Also note that ArrayAdapter NEEDS a mutable List (ArrayList),
        // or it will crash!
        super(context, 0, new ArrayList<>(goals));
//        this.onDeleteClick = onDeleteClick;
        this.activityModel = activityModel;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the flashcard for this position.
        var goal = getItem(position);
        assert goal != null;

        // Check if a view is being reused...
        ListItemGoalBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = ListItemGoalBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemGoalBinding.inflate(layoutInflater, parent, false);
        }

        if (goal.isCompleted()) {
            binding.goalCheckBox.setChecked(true);
        } else {
            binding.goalCheckBox.setChecked(false);
        }

        // Populate the view with the flashcard's data.
        binding.goalTitle.setText(goal.getName());

        binding.goalCheckBox.setOnClickListener(v -> {
            if (binding.goalCheckBox.isChecked()) {
                activityModel.getToday().getGoalRepository().setCompleted(goal.getId());
            } else {
                activityModel.getToday().getGoalRepository().setNonCompleted(goal.getId());
            }
        });

        binding.getRoot().setOnClickListener(v -> {
            Log.i("GoalListAdapter", "Goal item is clicked");
            if (binding.goalCheckBox.isChecked()) {
                binding.goalCheckBox.setChecked(false);
                activityModel.getToday().getGoalRepository().setNonCompleted(goal.getId());
            } else {
                binding.goalCheckBox.setChecked(true);
                activityModel.getToday().getGoalRepository().setCompleted(goal.getId());
            }
        });

        return binding.getRoot();
    }

    // The below methods aren't strictly necessary, usually.
    // But get in the habit of defining them because they never hurt
    // (as long as you have IDs for each item) and sometimes you need them.

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var goal = getItem(position);
        assert goal != null;

        var id = goal.getId();
        assert id != null;

        return id;
    }
}
