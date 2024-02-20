package edu.ucsd.cse110.successorator;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import java.time.LocalDate;
import java.util.List;

import edu.ucsd.cse110.successorator.data.db.RoomGoalRepository;
import edu.ucsd.cse110.successorator.data.db.SuccessoratorDatabase;
import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Day;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SimpleGoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;
import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class SuccessoratorApplication extends Application {
    private InMemoryDataSource dataSource;
    private GoalRepository goalRepository;
    private Day day;
    private SuccessDate oldDate;
    private MutableSubject<SuccessDate> successDate;
    private List<Goal> goals;

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
            goalRepository.save(InMemoryDataSource.DEFAULT_GOALS);

            sharePreferences.edit().putBoolean("isFirstRun", false).apply();
        }

//        this.dataSource = InMemoryDataSource.fromDefault();
//        this.goalRepository = new SimpleGoalRepository(dataSource);
        this.successDate = new SimpleSubject<>();
        updateDate();
        oldDate = this.successDate.getValue();
        this.day = new Day(successDate, goalRepository);


        getDay().getGoalRepository().findAll().observe(goals -> {
            if (goals == null) return;
            Log.i("updated goals", "updated goals");
            this.goals = goals;
        });

        // whenever date advances, Day will clear out old goals
        successDate.observe(newDate -> {
            assert newDate != null;
            if (!newDate.getYear().equals(oldDate.getYear())
                    || !newDate.getMonth().equals(oldDate.getMonth())
                    || !newDate.getDay().equals(oldDate.getDay()) ){

                oldDate = newDate;

//                var goals = this.goalRepository.findAll().getValue();
                // assert goals != null;
                if (goals != null) {
                    Log.i("updated goals", "goal is not null");

                    for (var goal : goals) {
                        if (goal.isCompleted()) {
                            goalRepository.remove(goal.getId());
                        }
                    }

                }
            }
        });

//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                updateDate();
//
//                handler.postDelayed(this,10000);
//            }
//        },10000);
    }

    private void updateDate() {
        LocalDate tempDate = LocalDate.now();
        this.successDate.setValue( new SuccessDate(tempDate.getYear(), tempDate.getMonth().getValue(), tempDate.getDayOfMonth()) );

    }

    public Day getDay() {
        return day;
    }
}
