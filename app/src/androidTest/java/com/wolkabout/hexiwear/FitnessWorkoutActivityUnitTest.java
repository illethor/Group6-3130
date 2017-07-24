package com.wolkabout.hexiwear;

import com.wolkabout.hexiwear.activity.FitnessWorkoutActivity;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class FitnessWorkoutActivityUnitTest {
    @Test
    public void testCleanEmail() {
        String email = "hello@mail.com";
        String cleanedEmail = FitnessWorkoutActivity.cleanEmail(email);
        assertEquals("hello@mailcom", cleanedEmail);
    }
}