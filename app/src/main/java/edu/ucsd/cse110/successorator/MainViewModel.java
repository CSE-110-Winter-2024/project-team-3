package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.Filter;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.RecurringType;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final @NonNull GoalRepository goalRepository;
    private final @NonNull MutableSubject<SuccessDate> todayDate;
    private final @NonNull MutableSubject<List<Goal>> displayGoals;
    private final @NonNull MutableSubject<String> topDateString;
    private final @NonNull MutableSubject<DisplayGoalType> displayGoalType;
    private final @NonNull Subject<List<Goal>> allGoals;

    private final @NonNull MutableSubject<String> focus;

    public MainViewModel(@NonNull GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        SuccessDate date = SuccessDate.fromJavaDate(new Date());
        this.todayDate = new SimpleSubject<>();
        this.displayGoals = new SimpleSubject<>();
        this.topDateString = new SimpleSubject<>();
        this.displayGoalType = new SimpleSubject<>();
        this.focus = new SimpleSubject<>();
        this.allGoals = goalRepository.findAll();

        this.todayDate.setValue(date);
        this.focus.setValue("All");
        this.topDateString.setValue("default");
        this.displayGoalType.setValue(DisplayGoalType.TODAY);

        this.todayDate.observe(successDate -> {
            updateGoalLists();
            updateTopDateString();
        });
        this.allGoals.observe(v -> {
            updateGoalLists();
            updateTopDateString();
        });
        this.displayGoalType.observe(v -> {
            updateGoalLists();
            updateTopDateString();
        });
        this.focus.observe(v -> {
            updateGoalLists();
            updateTopDateString();
        });

        updateGoalLists();
        updateTopDateString();
    }

    private void updateGoalLists() {
        List<Goal> displayGoalsTemp = new ArrayList<>();
        SuccessDate todayDateTemp = this.todayDate.getValue();
        assert todayDateTemp != null;
        List<Goal> allGoalsTemp = this.allGoals.getValue();

        if (allGoalsTemp == null) return;


        for (var goal : allGoalsTemp) {
            switch (Objects.requireNonNull(this.displayGoalType.getValue())) {
                case TODAY:
                    if (goal.ifDateMatchesRecurring(todayDateTemp)) {
                        displayGoalsTemp.add(goal);
                    }
                    break;
                case TOMORROW:
                    if (goal.ifDateMatchesRecurring(todayDateTemp.nextDay())) {
                        displayGoalsTemp.add(goal);
                    }
                    break;
                case PENDING:
                    if (goal.getAssignDate() == null) {
                        displayGoalsTemp.add(goal);
                        continue;
                    }
                    break;
                case RECURRING:
                    if (goal.getType() != RepeatType.ONE_TIME) displayGoalsTemp.add(goal);
                    break;
                default:
                    break;
            }
        }

        if (focus.getValue() != null) {
            displayGoalsTemp = Filter.filter_goals(displayGoalsTemp, focus.getValue());
        }

        this.displayGoals.setValue(displayGoalsTemp);
    }

    private void updateTopDateString() {
        SuccessDate todayDateTemp = this.todayDate.getValue();
        assert todayDateTemp != null;

        String displayDate;
        switch (Objects.requireNonNull(this.displayGoalType.getValue())) {
            case TODAY:
                displayDate = "Today  " +
                        todayDateTemp.getDayOfWeekString().substring(0, 3) + " " +
                        todayDateTemp.getMonth() + "/" +
                        todayDateTemp.getDay();
                break;
            case TOMORROW:
                displayDate = "Tomorrow  " +
                        todayDateTemp.nextDay().getDayOfWeekString().substring(0, 3) + " " +
                        todayDateTemp.nextDay().getMonth() + "/" +
                        todayDateTemp.nextDay().getDay();
                break;
            case PENDING:
                displayDate = "Pending";
                break;
            case RECURRING:
                displayDate = "Recurring";
                break;
            default:
                displayDate = "Error in Software";
                break;
        }

        this.topDateString.setValue(displayDate);
    }

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getGoalRepository());
                    });

    @NonNull
    public void mockAdvanceDay() {
        SuccessDate todayDateTemp = this.todayDate.getValue();
        List<Goal> allGoalsTemp = this.allGoals.getValue();

        if (allGoalsTemp == null || todayDateTemp == null) return;

        List<Goal> todayGoals = new ArrayList<>();
        List<Goal> tmrGoals = new ArrayList<>();

        Goal modifiedGoal = null;
        for (var goal : allGoalsTemp) {
            /**
             * IMPORTANT:: tomorrow check must come first, this is because today's recurring task
             * can be roll over to tomorrow, if we check today first, the logic for tomorrow will
             * override the roll-over.
             *
             * We want the roll-over to override whatever logic in finishing a task early
              */

            // tomorrow: need to "delete" completed goals & advance currIterDate
            if (goal.ifDateMatchesRecurring(todayDateTemp.nextDay())) {
                if (goal.getNextCompleted()) {
                    modifiedGoal = goal.withCurrIterDate(goal.calculateNextRecurring(todayDateTemp.nextDay()));
                    modifiedGoal = modifiedGoal.withNextComplete(false);
                }
            }

            // today: need to advance currIterDate if completed
            if (goal.ifDateMatchesRecurring(todayDateTemp)) {
                if (goal.getCurrCompleted()) {
                    modifiedGoal = goal.withCurrIterDate(goal.calculateNextRecurring(todayDateTemp));
                    modifiedGoal = modifiedGoal.withCurrComplete(false);
                } else {
                    modifiedGoal = goal.withCurrIterDate(todayDateTemp.toJavaDate());
                }
            }

            if (modifiedGoal != null) {
                goalRepository.save(modifiedGoal);
            }
        }

        todayDate.setValue(todayDate.getValue().nextDay());
    }

    public void putGoal(Goal goal) {
        goalRepository.append(goal);
    }

    public void setCompleted(Integer id) {
        switch (Objects.requireNonNull(displayGoalType.getValue())) {
            case PENDING:
            case TODAY:
                goalRepository.setCompleted(id);
                break;
            case TOMORROW:
                goalRepository.setNextCompleted(id);
                break;
            default:
                Log.e("MainViewModel", "Shouldn't be able to complete a recurring task");
                break;
        }
    }

    public void setNonCompleted(Integer id) {
        switch (Objects.requireNonNull(displayGoalType.getValue())) {
            case PENDING:
            case TODAY:
                goalRepository.setNonCompleted(id);
                break;
            case TOMORROW:
                goalRepository.setNextNonCompleted(id);
                break;
            default:
                Log.e("MainViewModel", "Shouldn't be able to complete a recurring task");
                break;
        }
    }

    @NonNull
    public Subject<SuccessDate> getTodayDate() {
        return todayDate;
    }

    @NonNull
    public Subject<List<Goal>> getDisplayGoals() {
        return displayGoals;
    }

    @NonNull
    public Subject<String> getTopDateString() {
        return topDateString;
    }

    public void setDisplayGoalType(@NonNull DisplayGoalType displayGoalType) {
        this.displayGoalType.setValue(displayGoalType);
    }
}
