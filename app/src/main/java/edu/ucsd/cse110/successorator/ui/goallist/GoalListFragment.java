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

import edu.ucsd.cse110.successorator.MainActivity;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;
import edu.ucsd.cse110.successorator.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.dialog.CreateSelectGoalTypeFragment;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.goalList.setAdapter(adapter);

        view.nextDayButton.setOnClickListener(v -> {
            activityModel.mockAdvanceDay();
        });

        activityModel.getTodayDate().observe(newDate -> {
            if (newDate != null) {
                String displayDate = newDate.getDayOfWeekString() + "  " + newDate.getMonth() + "/" + newDate.getDay() + "/" + newDate.getYear();
                this.view.dateText.setText(displayDate);
            }
        });

        view.addTaskButton.setOnClickListener(v -> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getChildFragmentManager(), "test");
        });

        view.typeMenu.setOnClickListener(v -> {
            var goalDialogFragment = CreateSelectGoalTypeFragment.newInstance();
            goalDialogFragment.show(getChildFragmentManager(), "SelectGoalType");
        });

        view.focusMenuButton.setOnClickListener(v-> {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show(getChildFragmentManager(), "test");
        });


        activityModel.getTodayGoals().observe(goalsSubject -> {
            if (goalsSubject == null) return;

            if (pastObserver != null) {
                goalsSubject.removeObserver(pastObserver);
            }

            pastObserver = new GoalListObserver(adapter, view);

            goalsSubject.observe(pastObserver);
        });

        return view.getRoot();
    }

}
