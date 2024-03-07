package edu.ucsd.cse110.successorator.lib.selector;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.GoalRepository;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface FilterGoalByType {
    enum FilterType {
        TODAY,
        TOMORROW,
        PENDING,
        RECURRING
    };

    public Subject<List<Goal>> getFilteredSubject(GoalRepository goalRepository);
    public FilterType getFilterType();
}
