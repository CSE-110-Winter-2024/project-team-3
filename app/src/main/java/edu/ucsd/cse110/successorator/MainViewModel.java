package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final @NonNull GoalRepository goalRepository;
    private final @NonNull MutableSubject<SuccessDate> todayDate;
    private final @NonNull MutableSubject<SuccessDate> tmrDate;
    private @NonNull MutableSubject<Subject<List<Goal>>> todayGoals;
    private @NonNull MutableSubject<Subject<List<Goal>>> tmrGoals;
    private final @NonNull Subject<List<Goal>> pendingGoals;
    private final @NonNull Subject<List<Goal>> recurringGoals;

    public MainViewModel(@NonNull GoalRepository goalRepository) {
        this.goalRepository = goalRepository;

        SuccessDate date = SuccessDate.fromJavaDate(new Date());
        this.todayDate = new SimpleSubject<>();
        this.tmrDate = new SimpleSubject<>();
        this.todayGoals = new SimpleSubject<>();
        this.tmrGoals = new SimpleSubject<>();

        this.todayDate.observe(successDate -> {
            if (successDate != null) {
                this.todayGoals.setValue(goalRepository.findAll(successDate));
            }
        });
        this.tmrDate.observe(successDate -> {
            if (successDate != null) {
                this.tmrGoals.setValue(goalRepository.findAll(successDate));
            }
        });
        this.pendingGoals = goalRepository.findPending();
        this.recurringGoals = goalRepository.findRecurring();


        this.todayDate.setValue(date);
        this.tmrDate.setValue(date.nextDay());
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
    public MutableSubject<SuccessDate> getTodayDate() {
        return todayDate;
    }

    @NonNull
    public MutableSubject<SuccessDate> getTmrDate() {
        return tmrDate;
    }

    @NonNull
    public MutableSubject<Subject<List<Goal>>> getTodayGoals() {
        return todayGoals;
    }

    @NonNull
    public MutableSubject<Subject<List<Goal>>> getTmrGoals() {
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
