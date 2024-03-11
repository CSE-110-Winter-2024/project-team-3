package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
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
    }
    private final @NonNull GoalRepository goalRepository;
    private final @NonNull MutableSubject<SuccessDate> todayDate;
    private final @NonNull MutableSubject<List<Goal>> displayGoals;
    private final @NonNull MutableSubject<String> topDateString;
    private final @NonNull MutableSubject<DisplayGoalType> displayGoalType;
    private final @NonNull Subject<List<Goal>> allGoals;

    public MainViewModel(@NonNull GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        SuccessDate date = SuccessDate.fromJavaDate(new Date());
        this.todayDate = new SimpleSubject<>();
        this.displayGoals = new SimpleSubject<>();
        this.topDateString = new SimpleSubject<>();
        this.displayGoalType = new SimpleSubject<>();
        this.allGoals = goalRepository.findAll();

        this.todayDate.observe(successDate -> {
            updateGoalLists();
        });
        this.allGoals.observe(v -> {
            updateGoalLists();
        });

        this.topDateString.setValue("default");
        this.todayDate.setValue(date);
        this.displayGoalType.setValue(DisplayGoalType.RECURRING);
    }

    private void updateGoalLists() {
        List<Goal> displayGoals = new ArrayList<>();
        SuccessDate todayDateTemp = this.todayDate.getValue();
        List<Goal> allGoals = this.allGoals.getValue();

        if (allGoals == null) return;

        String displayDate;

        for (var goal : allGoals) {
            switch (this.displayGoalType.getValue()) {
                case TODAY:
                    if (goal.ifDateMatchesRecurring(todayDateTemp)) {
                        displayGoals.add(goal);
                    }
                    displayDate = "Today " +
                            todayDateTemp.getDayOfWeekString().substring(0, 3) + "  " +
                            todayDateTemp.getMonth() + "/" +
                            todayDateTemp.getDay();
                    this.topDateString.setValue(displayDate);
                    break;
                case TOMORROW:
                    if (goal.ifDateMatchesRecurring(todayDateTemp.nextDay())) {
                        displayGoals.add(goal);
                    }
                    displayDate = "Tomorrow " +
                            todayDateTemp.nextDay().getDayOfWeekString().substring(0, 3) + "  " +
                            todayDateTemp.nextDay().getMonth() + "/" +
                            todayDateTemp.nextDay().getDay();
                    this.topDateString.setValue(displayDate);
                    break;
                case PENDING:
                    if (goal.getAssignDate() == null) {
                        displayGoals.add(goal);
                        continue;
                    }
                    this.topDateString.setValue("Pending");
                    break;
                case RECURRING:
                    if (goal.getType() != RepeatType.ONE_TIME) displayGoals.add(goal);
                    this.topDateString.setValue("Recurring");
                    break;
            }
        }


        this.displayGoals.setValue(displayGoals);
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
