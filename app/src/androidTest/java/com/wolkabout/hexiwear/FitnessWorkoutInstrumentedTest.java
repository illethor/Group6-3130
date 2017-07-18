package com.wolkabout.hexiwear;

import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessWorkoutActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class FitnessWorkoutInstrumentedTest {

    @Rule
    public final ActivityTestRule<FitnessWorkoutActivity> mActivityRule =
            new ActivityTestRule<>(FitnessWorkoutActivity.class);

    /**
     * Tests for UI elements present on page
     */
    @Test
    public void testUI() {
        onView(withId(R.id.tv_stepNum))
                .check(matches(withText("0")));

        onView(withId(R.id.tv_steps))
                .check(matches(withText("Steps")));

        onView(withId(R.id.textViewM))
                .check(matches(withText("00")));

        onView(withId(R.id.textViewS))
                .check(matches(withText("00")));

        onView(withId(R.id.textView))
                .check(matches(withText(":")));
    }


    /**
     * Test start and stop buttons
     */
    @Test
    public void testStartAndFinish() {
        onView(withId(R.id.Start))
                .perform(click());

        onView(withId(R.id.Stop))
                .perform(click());
    }
}
