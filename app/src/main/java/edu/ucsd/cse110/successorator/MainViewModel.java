package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

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

    enum DisplayGoalType {
        TODAY,
        TOMORROW,
        PENDING,
        RECURRING
    };
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
        });
        this.allGoals.observe(v -> {
            updateGoalLists();
        });

        updateGoalLists();
    }

    private void updateGoalLists() {
        List<Goal> displayGoalsTemp = new ArrayList<>();
        SuccessDate todayDateTemp = this.todayDate.getValue();
        assert todayDateTemp != null;
        List<Goal> allGoalsTemp = this.allGoals.getValue();

        if (allGoalsTemp == null) return;

        String displayDate;

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


        switch (Objects.requireNonNull(this.displayGoalType.getValue())) {
            case TODAY:
                displayDate = "Today  " +
                        todayDateTemp.getDayOfWeekString().substring(0, 3) + " " +
                        todayDateTemp.getMonth() + "/" +
                        todayDateTemp.getDay();
                this.topDateString.setValue(displayDate);
                break;
            case TOMORROW:
                displayDate = "Tomorrow  " +
                        todayDateTemp.nextDay().getDayOfWeekString().substring(0, 3) + " " +
                        todayDateTemp.nextDay().getMonth() + "/" +
                        todayDateTemp.nextDay().getDay();
                this.topDateString.setValue(displayDate);
                break;
            case PENDING:
                this.topDateString.setValue("Pending");
                break;
            case RECURRING:
                this.topDateString.setValue("Recurring");
                break;
        }

        if (focus.getValue() != null) {
            displayGoalsTemp = Filter.filter_goals(displayGoalsTemp, focus.getValue());
        }

        this.displayGoals.setValue(displayGoalsTemp);
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
        todayDate.setValue(todayDate.getValue().nextDay());
    }

    public void putGoal(Goal goal) {
        goalRepository.append(goal);
    }

    public void setCompleted(Integer id) {
        goalRepository.setCompleted(id);
    }

    public void setNonCompleted(Integer id) {
        goalRepository.setNonCompleted(id);
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
}
