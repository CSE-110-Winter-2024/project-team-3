package edu.ucsd.cse110.successorator;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.MockDay;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SuccessoratorApplication extends Application {
    private InMemoryDataSource dataSource;
    private GoalRepository goalRepository;
    private MockDay day;
    private Subject<SuccessDate> successDate;

    @Override
    public void onCreate() {
        super.onCreate();

        /*
        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        SECardsDatabase.class,
                        "secards-database"
                )
                .allowMainThreadQueries()
                .build();

        this.flashcardRepository =
                new RoomFlashcardRepository(database.flashcardDao());

        var sharePreferences = getSharedPreferences("secards", MODE_PRIVATE);
        var isFirstRun = sharePreferences.getBoolean("isFirstRun", true);

        if (isFirstRun && database.flashcardDao().count() == 0) {
            flashcardRepository.save(InMemoryDataSource.DEFAULT_CARDS);

            sharePreferences.edit().putBoolean("isFirstRun", false).apply();
        }
         */
        this.dataSource = InMemoryDataSource.fromDefault();
        this.goalRepository = new SimpleGoalRepository(dataSource);
        this.successDate = new SimpleSubject<>();
        updateDate();
        this.day = new MockDay(successDate, goalRepository);
    }

    private void updateDate() {
        LocalDate tempDate = LocalDate.now();
        this.successDate.setValue( new SuccessDate(tempDate.getYear(), tempDate.getMonth().getValue(), tempDate.getDayOfMonth()) );

    }

    public Day getDay() {
        return day;
    }
    @NonNull
    public MockDay getMockDay() {
        return day;
    }
}
