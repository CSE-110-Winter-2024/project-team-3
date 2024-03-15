package edu.ucsd.cse110.successorator;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import edu.ucsd.cse110.successorator.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryGoalSource;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;

public class SuccessoratorApplication extends Application {
    private GoalRepository goalRepository;

    @Override
    public void onCreate() {
        super.onCreate();


        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        SuccessoratorDatabase.class,
                        "secards-database"
                )
                .allowMainThreadQueries()
                .build();

        this.goalRepository =
                new RoomGoalRepository(database.goalDao());

        var sharePreferences = getSharedPreferences("secards", MODE_PRIVATE);
        var isFirstRun = sharePreferences.getBoolean("isFirstRun", true);

        if (isFirstRun && database.goalDao().count() == 0) {

            sharePreferences.edit().putBoolean("isFirstRun", false).apply();
        }

    }


    public GoalRepository getGoalRepository() {
        return goalRepository;
    }
}
