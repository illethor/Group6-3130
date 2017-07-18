package com.wolkabout.hexiwear;

import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessMainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by David on 2017-06-01.
 */

public class MainInstrumentedTest {


    @Rule
    public final ActivityTestRule<FitnessMainActivity> mActivityRule =
            new ActivityTestRule<>(FitnessMainActivity.class);

    // Test Graph Button
    @Test
    public void graphTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.graphsBtn))
                .perform(click());
    }

    // Test FitnessWorkout Button
    @Test
    public void workoutTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());
    }
}
