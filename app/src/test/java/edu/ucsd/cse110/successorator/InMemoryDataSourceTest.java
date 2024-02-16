package edu.ucsd.cse110.successorator;

import org.junit.Test;

import java.util.Date;

import edu.ucsd.cse110.successorator.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

import static org.junit.Assert.*;


public class InMemoryDataSourceTest {
    Goal test1 = new Goal("Do dishes", "Make sure to use lots of soap!", 0, 0, false, new Date(), new Date());

    @Test
    public void emptyInsert() {
        InMemoryDataSource dataSource = new InMemoryDataSource();
        assertEquals(dataSource.getGoals().size(), 0);

        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 1);

        dataSource = new InMemoryDataSource();
        dataSource.putGoals(InMemoryDataSource.DEFAULT_GOALS);
        assertEquals(dataSource.getGoals().size(), 6);
    }

    @Test
    public void nonEmptyInsert() {
        InMemoryDataSource dataSource = new InMemoryDataSource();
        dataSource.putGoals(InMemoryDataSource.DEFAULT_GOALS);
        assertEquals(dataSource.getGoals().size(), 6);

        var before = dataSource.getGoal(dataSource.getGoals().size() - 1);
        dataSource.putGoal(test1);
        assertEquals(dataSource.getGoals().size(), 7);

        var after = dataSource.getGoal(dataSource.getGoals().size() - 2);
        assertEquals(before, after);
        assertNotEquals(before, dataSource.getGoal(dataSource.getGoals().size() - 1));
    }
}