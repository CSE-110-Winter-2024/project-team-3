package edu.ucsd.cse110.successorator.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.DisplayGoalType;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.FocusType;
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
        binding.goalReccuringText.setText(goal.getDescription());
        addFocusColor(goal, binding);

        boolean ifChecked = false;
        switch (Objects.requireNonNull(activityModel.getDisplayGoalType().getValue())) {
            case TODAY:
                ifChecked = goal.getCurrCompleted();
                break;
            case TOMORROW:
                ifChecked = goal.getNextCompleted();
                break;
            default:
                ifChecked = false;
                break;
        }
        if (ifChecked) {
            binding.goalCheckBox.setChecked(true);
            binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.goalReccuringText.setPaintFlags(binding.goalReccuringText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));
        } else {
            binding.goalCheckBox.setChecked(false);
            binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            binding.goalReccuringText.setPaintFlags(binding.goalReccuringText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            addFocusColor(goal, binding);
        }

        switch (Objects.requireNonNull(activityModel.getDisplayGoalType().getValue())) {
            case TODAY:
            case TOMORROW:
                binding.getRoot().setOnClickListener(v -> {
                    if (binding.goalCheckBox.isChecked()) {
                        binding.goalCheckBox.setChecked(false);
                        binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        binding.goalReccuringText.setPaintFlags(binding.goalReccuringText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        activityModel.setNonCompleted(goal.getId());
                        addFocusColor(goal, binding);
                    } else {
                        binding.goalCheckBox.setChecked(true);
                        binding.goalTitle.setPaintFlags(binding.goalTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        binding.goalReccuringText.setPaintFlags(binding.goalReccuringText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        activityModel.setCompleted(goal.getId());
                        binding.focusTypeLabel.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));
                    }
                });
                break;
            case PENDING:
            case RECURRING:
                break;
        };

        binding.getRoot().setOnLongClickListener(v -> {
            switch (Objects.requireNonNull(activityModel.getDisplayGoalType().getValue())) {
                case TODAY:
                case TOMORROW:
                    showMoveGoalMenu(binding, goal, false, true);
                    break;
                case RECURRING:
                    showMoveGoalMenu(binding, goal, false, false);
                    break;
                case PENDING:
                    showMoveGoalMenu(binding, goal, true, true);
                    break;
            }
            return true;
        });

        return binding.getRoot();
    }

    private void addFocusColor(Goal goal, ListItemGoalBinding binding) {
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
    }

    private void showMoveGoalMenu(ListItemGoalBinding binding, Goal goal, boolean showMoveGoal, boolean showFinishGoal) {
        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.goalTitle);

        // Inflating popup menu from popup_menu.xml file
        Menu view = popupMenu.getMenu();
        popupMenu.getMenuInflater().inflate(R.menu.move_goal_popup_menu, view);
        if (!showMoveGoal) {
            view.getItem(0).setVisible(false);
            view.getItem(1).setVisible(false);
        }
        if (!showFinishGoal) {
            view.getItem(2).setVisible(false);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (Objects.requireNonNull(menuItem.getTitle()).toString()) {
                    case "Move to Today":
                        activityModel.moveGoalToToday(goal);
                        break;
                    case "Move to Tomorrow":
                        activityModel.moveGoalToTomorrow(goal);
                        break;
                    case "Finish":
                        activityModel.setCompleted(goal.getId());

                        if (activityModel.getDisplayGoalType().getValue() == DisplayGoalType.PENDING) {
                            activityModel.moveGoalToToday(goal);
                        }
                        break;
                    case "Delete":
                        activityModel.deleteGoal(goal.getId());
                        break;
                }
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
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
