package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class Filter {

    public static List<Goal> filter_goals(@NonNull List<Goal> list_goals,@NonNull FocusType focusType){
        List<Goal> filtered_goals = new ArrayList<>();
        for(Goal curr_goal : list_goals){
            if (focusType == FocusType.ALL){
                filtered_goals.add(curr_goal);
            }
            else if (curr_goal.get_focus() == focusType){
                filtered_goals.add(curr_goal);
            }

        }
        return filtered_goals;
    }
}
