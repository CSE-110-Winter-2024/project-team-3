package edu.ucsd.cse110.successorator;

import android.app.Application;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.ListOfGoalRecord;
import edu.ucsd.cse110.successorator.lib.domain.SimpleListOfGoalRecord;

public class SuccessoratorApplication extends Application {
    private ListOfGoalRecord listOfGoalRecord;

    @Override
    public void onCreate() {
        super.onCreate();

        InMemoryDataSource dataSource;
        dataSource = InMemoryDataSource.fromDefault();
        this.listOfGoalRecord = new SimpleListOfGoalRecord(dataSource);


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


    public ListOfGoalRecord getListOfGoalRecord() {
        return listOfGoalRecord;
    }
}
