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
    private final @NonNull GoalRepository goalRepository;
    private final @NonNull MutableSubject<SuccessDate> todayDate;
    private final @NonNull MutableSubject<SuccessDate> tmrDate;
    private final @NonNull MutableSubject<List<Goal>> todayGoals;
    private final @NonNull MutableSubject<List<Goal>> tmrGoals;
    private final @NonNull MutableSubject<List<Goal>> pendingGoals;
    private final @NonNull MutableSubject<List<Goal>> recurringGoals;
    private final @NonNull Subject<List<Goal>> allGoals;

    public MainViewModel(@NonNull GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        SuccessDate date = SuccessDate.fromJavaDate(new Date());
        this.todayDate = new SimpleSubject<>();
        this.tmrDate = new SimpleSubject<>();
        this.todayGoals = new SimpleSubject<>();
        this.tmrGoals = new SimpleSubject<>();
        this.pendingGoals = new SimpleSubject<>();
        this.recurringGoals = new SimpleSubject<>();

        this.allGoals = goalRepository.findAll();

        this.todayDate.observe(successDate -> {
            updateGoalLists();
        });
        this.tmrDate.observe(successDate -> {
            updateGoalLists();
        });
        this.allGoals.observe(v -> {
            updateGoalLists();
        });


        this.todayDate.setValue(date);
        this.tmrDate.setValue(date.nextDay());
    }

    private void updateGoalLists() {
        List<Goal> todayGoals = new ArrayList<>();
        List<Goal> tmrGoals = new ArrayList<>();
        List<Goal> pendingGoals = new ArrayList<>();
        List<Goal> recurringGoals = new ArrayList<>();

        SuccessDate todayDate = this.todayDate.getValue();
        SuccessDate tmrDate = this.tmrDate.getValue();

        List<Goal> allGoals = this.allGoals.getValue();
        if (allGoals == null) return;

        for (var goal : allGoals) {
            if (goal.getType() != RepeatType.ONE_TIME) recurringGoals.add(goal);
            if (goal.getAssignDate() == null) {
                pendingGoals.add(goal);
                continue;
            }

            // on the same day
            if (goal.ifDateMatchesRecurring(todayDate)) {
                todayGoals.add(goal);
            }
            if (goal.ifDateMatchesRecurring(tmrDate)) {
                tmrGoals.add(goal);
            }
        }

        this.todayGoals.setValue(todayGoals);
        this.tmrGoals.setValue(tmrGoals);
        this.pendingGoals.setValue(pendingGoals);
        this.recurringGoals.setValue(recurringGoals);
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
        tmrDate.setValue(tmrDate.getValue().nextDay());


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
    public Subject<SuccessDate> getTmrDate() {
        return tmrDate;
    }

    @NonNull
    public Subject<List<Goal>> getTodayGoals() {
        return todayGoals;
    }

    @NonNull
    public Subject<List<Goal>> getTmrGoals() {
        return tmrGoals;
    }

    @NonNull
    public Subject<List<Goal>> getPendingGoals() {
        return pendingGoals;
    }

    @NonNull
    public Subject<List<Goal>> getRecurringGoals() {
        return recurringGoals;
    }
}
