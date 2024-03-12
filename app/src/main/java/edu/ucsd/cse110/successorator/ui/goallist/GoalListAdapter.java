package edu.ucsd.cse110.successorator.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
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

        // Populate the view with the flashcard's data.
        binding.goalTitle.setText(goal.getName());
        switch (goal.get_focus()) {
            case HOME:
                binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.H_color));
                break;
            case WORK:
                binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.W_color));
                break;
            case ERRANDS:
                binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.E_color));
                break;
            case SCHOOL:
                binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.S_color));
                break;
        }
        binding.focusTypeLabel.setText(goal.get_focus().name().substring(0, 1));


        if (goal.getCurrCompleted()) {
            binding.goalCheckBox.setChecked(true);
            binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            binding.goalCheckBox.setChecked(false);
            binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        binding.getRoot().setOnClickListener(v -> {
            Log.i("GoalListAdapter", "Goal item is clicked");
            if (binding.goalCheckBox.isChecked()) {
                binding.goalCheckBox.setChecked(false);
                binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                activityModel.setNonCompleted(goal.getId());
            } else {
                binding.goalCheckBox.setChecked(true);
                binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                activityModel.setCompleted(goal.getId());
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
