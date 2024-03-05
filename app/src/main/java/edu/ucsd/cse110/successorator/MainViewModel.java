package edu.ucsd.cse110.successorator;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.time.LocalDate;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.ListOfGoalRecord;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class MainViewModel extends ViewModel {
    private final @NonNull ListOfGoalRecord listOfGoalRecord;
    private final @NonNull MutableSubject<Day> today;
    private final @NonNull MutableSubject<Day> tmr;

    public MainViewModel(@NonNull ListOfGoalRecord listOfGoalRecord) {
        this.listOfGoalRecord = listOfGoalRecord;
        this.today = new SimpleSubject<>();
        this.tmr = new SimpleSubject<>();

        LocalDate tempDate = LocalDate.now();
        SuccessDate date = new SuccessDate(tempDate.getYear(), tempDate.getMonth().getValue(), tempDate.getDayOfMonth());


        this.today.setValue(
                listOfGoalRecord.createDay(date)
        );
        this.tmr.setValue(
                listOfGoalRecord.createDay(date.nextDay())
        );
    }

    public static final ViewModelInitializer<MainViewModel> initializer =
            new ViewModelInitializer<>(
                    MainViewModel.class,
                    creationExtras -> {
                        var app = (SuccessoratorApplication) creationExtras.get(APPLICATION_KEY);
                        assert app != null;
                        return new MainViewModel(app.getListOfGoalRecord());
                    });

    public Subject<Day> getDaySubject() {
        return this.today;
    }

    @NonNull
    public Day getToday() {
        assert today.getValue() != null;
        return today.getValue();
    }

    @NonNull
    public void rollOverTmrToToday() {
        SuccessDate oldDate = getToday().getDate();
        SuccessDate newDate = oldDate.nextDay().nextDay();

        var thirdDay = getListOfGoalRecord().createDay(newDate);

        //TODO:: !!!!!!!!!!!!!!!!!!!!!!!
    }

    @NonNull
    public ListOfGoalRecord getListOfGoalRecord() {
        return listOfGoalRecord;
    }
}
