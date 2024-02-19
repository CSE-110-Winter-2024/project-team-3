package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

public class MainViewModel extends ViewModel {
    private final @NonNull Day day;

    public MainViewModel(Day day) {
        this.day = day;
    }

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getDay());
                    });

    @NonNull
    public Day getDay() {
        return day;
    }
}