package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;

public class Filter {
    private final @NonNull List<Goal> list_goals;
    private final @NonNull String focus;

    public Filter(@NonNull List<Goal> list_goals,@NonNull String focus){
        this.list_goals = list_goals;
        this.focus = focus;
    }

    public List<Goal> filter_goals(){
        List<Goal> filtered_goals = new ArrayList<Goal>();
        for(Goal curr_goal : this.list_goals){
            if (focus.equals("All")){
                filtered_goals.add(curr_goal);
            }
            else if (curr_goal.get_focus().equals(this.focus)){
                filtered_goals.add(curr_goal);
            }

        }
        return filtered_goals;
    }
}
