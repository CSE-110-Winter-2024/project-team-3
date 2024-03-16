package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

/**
 * Tests for SuccessDate Class type
 */
public class SuccessDateTest {
    @Test
    public void getDay() {
        LocalDate now = LocalDate.of( LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        SuccessDate sd = new SuccessDate(now.getYear(), now.getMonth().getValue(), now.getDayOfMonth());

        assertEquals(now.getYear(), sd.getYear().intValue());
        assertEquals(now.getMonth().getValue(), sd.getMonth().intValue());
        assertEquals(now.getDayOfMonth(), sd.getDay().intValue());
    }

    @Test
    public void compareTomorrow() {
        // Case of only day change
        LocalDate arbTimeTomorrow = LocalDate.of(2000, 1, 2);

        SuccessDate today = new SuccessDate(2000, 1, 1);
        var tomorrow = today.nextDay();

        assertEquals(arbTimeTomorrow.getYear(), tomorrow.getYear().intValue());
        assertEquals(arbTimeTomorrow.getMonth().getValue(), tomorrow.getMonth().intValue());
        assertEquals(arbTimeTomorrow.getDayOfMonth(), tomorrow.getDay().intValue());


        // Case of month overflow
        arbTimeTomorrow = LocalDate.of(2000, 2, 1);

        today = new SuccessDate(2000, 1, 31);
        tomorrow = today.nextDay();

        assertEquals(arbTimeTomorrow.getYear(), tomorrow.getYear().intValue());
        assertEquals(arbTimeTomorrow.getMonth().getValue(), tomorrow.getMonth().intValue());
        assertEquals(arbTimeTomorrow.getDayOfMonth(), tomorrow.getDay().intValue());


        // Case of year overflow
        arbTimeTomorrow = LocalDate.of(2001, 1, 1);
        today = new SuccessDate(2000, 12, 31);
        tomorrow = today.nextDay();

        assertEquals(arbTimeTomorrow.getYear(), tomorrow.getYear().intValue());
        assertEquals(arbTimeTomorrow.getMonth().getValue(), tomorrow.getMonth().intValue());
        assertEquals(arbTimeTomorrow.getDayOfMonth(), tomorrow.getDay().intValue());
    }

}