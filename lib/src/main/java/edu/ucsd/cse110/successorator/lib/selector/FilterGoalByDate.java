package edu.ucsd.cse110.successorator.lib.selector;

import androidx.annotation.NonNull;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class FilterGoalByDate implements FilterGoalByType{
    final private @NonNull Subject<SuccessDate> successDateSubject;

    public FilterGoalByDate(@NonNull Subject<SuccessDate> successDateSubject) {
        this.successDateSubject = successDateSubject;
    }

    public Subject<List<Goal>> getFilteredSubject(GoalRepository goalRepository){
        return goalRepository.findAll(successDateSubject.getValue());
    }

    public FilterType getFilterType(){
        return FilterType.TODAY;
    }
}
