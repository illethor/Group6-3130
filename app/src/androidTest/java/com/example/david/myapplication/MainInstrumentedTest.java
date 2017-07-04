package com.example.david.myapplication;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by David on 2017-06-01.
 */

public class MainInstrumentedTest {


    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    // Test Graph Button
    @Test
    public void graphTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.graphsBtn))
                .perform(click());
    }

    // Test Workout Button
    @Test
    public void workoutTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());
    }
}
