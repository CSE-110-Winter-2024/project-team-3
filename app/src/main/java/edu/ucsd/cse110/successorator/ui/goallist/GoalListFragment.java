package edu.ucsd.cse110.successorator.ui.goallist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;


public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private GoalListAdapter adapter;

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
        activityModel.getToday().getGoalRepository().findAll().observe(goals -> {
            if (goals == null) return;

            List<Goal> nonCompletedGoals = new ArrayList<>();
            List<Goal> completedGoals = new ArrayList<>();

            for (var goal:goals) {
                if (goal.isCompleted()) {
                    completedGoals.add(goal);
                } else {
                    nonCompletedGoals.add(goal);
                }
            }

            completedGoals = completedGoals.stream()
                    .sorted(Comparator.comparing(Goal::getDate)).collect(Collectors.toList());
            nonCompletedGoals = nonCompletedGoals.stream()
                    .sorted(Comparator.comparing(Goal::getDate)).collect(Collectors.toList());


            ArrayList<Goal> newOrderedGoals = new ArrayList<>();
            newOrderedGoals.addAll(nonCompletedGoals);
            newOrderedGoals.addAll(completedGoals);

            adapter.clear();
            adapter.addAll(newOrderedGoals); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.goalList.setAdapter(adapter);

        activityModel.getDaySubject().observe(newDay -> {
            assert newDay != null;
            var newDate = newDay.getDate();
            String displayDate = newDate.getDayOfWeek() + "  " + newDate.getMonth() + "/" + newDate.getDay() + "/" + newDate.getYear();
            this.view.dateText.setText(displayDate);
        });

        view.nextDayButton.setOnClickListener(v -> {
            activityModel.rollOverTmrToToday();
        });

        return view.getRoot();
    }
}
