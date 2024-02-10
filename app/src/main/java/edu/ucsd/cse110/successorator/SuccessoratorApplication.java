package edu.ucsd.cse110.successorator;

import android.app.Application;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;

public class SuccessoratorApplication extends Application {
    private InMemoryDataSource dataSource;
    private GoalRepository goalRepository;

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
    }

    public GoalRepository getGoalRepository() {
        return goalRepository;
    }
}
