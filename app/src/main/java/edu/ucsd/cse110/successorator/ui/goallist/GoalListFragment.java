package edu.ucsd.cse110.successorator.ui.goallist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.DisplayGoalType;
import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.lib.domain.FocusType;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.util.Observer;

public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private GoalListAdapter adapter;

    private Observer<List<Goal>> pastObserver = null;

    public GoalListFragment() {
        // Required empty public constructor
    }

    public static GoalListFragment newInstance() {
        GoalListFragment fragment = new GoalListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

//         Initialize the Adapter (with an empty list for now)
        this.adapter = new GoalListAdapter(requireContext(), List.of(), activityModel);

//        this.adapter = new CardListAdapter(requireContext(), List.of(), activityModel::remove);

    }

    private void showGoalTypeMenu() {

        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(getContext(), view.typeMenu);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.goal_type_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String goalTypeString = Objects.requireNonNull(menuItem.getTitle()).toString().toUpperCase();
                DisplayGoalType displayGoalType = DisplayGoalType.valueOf(goalTypeString);
                activityModel.setDisplayGoalType(displayGoalType);
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }


    private void showFocusTypeMenu() {

        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(getContext(), view.focusMenuButton);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.focus_mode_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String focusTypeString = Objects.requireNonNull(menuItem.getTitle()).toString().toUpperCase();
                if (focusTypeString.equals("CANCEL")) focusTypeString = "ALL";
                FocusType focusType = FocusType.valueOf(focusTypeString);
                activityModel.setFocusType(focusType);
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.goalList.setAdapter(adapter);

        view.nextDayButton.setOnClickListener(v -> {
            activityModel.mockAdvanceDay();
        });

        activityModel.getTopDateString().observe(newTopDateString -> {
            view.dateText.setText(newTopDateString);
        });

        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getChildFragmentManager(), "test");
        });

        view.typeMenu.setOnClickListener(v -> {
            showGoalTypeMenu();
        });
        view.dateText.setOnClickListener(v -> {
            showGoalTypeMenu();
        });

        view.focusMenuButton.setOnClickListener(v -> {
            showFocusTypeMenu();
        });


        activityModel.getDisplayGoals().observe(goals -> {
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
                    .sorted(Comparator.comparing(Goal::getAssignDate))
                    .sorted(Comparator.comparing(Goal::get_focus))
                    .collect(Collectors.toList());
            nonCompletedGoals = nonCompletedGoals.stream()
                    .sorted(Comparator.comparing(Goal::getAssignDate))
                    .sorted(Comparator.comparing(Goal::get_focus))
                    .collect(Collectors.toList());


            ArrayList<Goal> newOrderedGoals = new ArrayList<>();
            newOrderedGoals.addAll(nonCompletedGoals);
            newOrderedGoals.addAll(completedGoals);

            adapter.clear();
            adapter.addAll(newOrderedGoals); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });

        return view.getRoot();
    }
}
