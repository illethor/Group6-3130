package com.wolkabout.hexiwear;

import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessSetRangesActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class SetRangesInstrumentedTest {

    @Rule
    public final ActivityTestRule<FitnessSetRangesActivity> mActivityRule =
            new ActivityTestRule<>(FitnessSetRangesActivity.class);

    /**
     * Tests for UI elements present on page
     */
    @Test
    public void testUI() {
        onView(withId(R.id.tvSelection))
                .check(matches(withText("Select your workout type:")));

        onView(withId(R.id.tvLowerBound))
                .check(matches(withText("Heartrate Lower Bound:")));

        onView(withId(R.id.tvUpperBound))
                .check(matches(withText("Heartrate Upper Bound:")));
    }

    /**
     * Test spinner for expected workout type values.
     */
    @Test
    public void testSpinner() {
        onView(withId(R.id.spnInfo))
                .perform(click());

        onData(hasToString(startsWith("Walking")))
                .perform(click());

        onView(withId(R.id.spnInfo))
                .check(matches(withSpinnerText(containsString("Walking"))));

        onView(withId(R.id.spnInfo))
                .perform(click());

        onData(hasToString(startsWith("Running")))
                .perform(click());

        onView(withId(R.id.spnInfo))
                .check(matches(withSpinnerText(containsString("Running"))));
    }

    /**
     * Test lower bound edit text field in UI
     */
    @Test
    public void testLBEditTextRanges() {
        onView(withId(R.id.etLowerBound))
                .perform(click())
                .perform(typeText("123sadsd4"))
                .check(matches(withText("1234")));
    }

    /**
     * Test upper bound edit text field in UI
     */
    @Test
    public void testUBEditTextRanges() {
        onView(withId(R.id.etUpperBound))
                .perform(click())
                .perform(typeText("123sadsd4"))
                .check(matches(withText("1234")));
    }

    /**
     * Test upper bound edit text field in UI
     */
    @Test
    public void testApplyButton() {
        //Spinner
        onView(withId(R.id.spnInfo))
                .perform(click());
        onData(hasToString(startsWith("Walking")))
                .perform(click());

        //Bounds
        onView(withId(R.id.etLowerBound))
                .perform(click())
                .perform(typeText("50"));

        pressBack();

        onView(withId(R.id.etUpperBound))
                .perform(click())
                .perform(typeText("150"));

        pressBack();

        //Apply
        onView(withId(R.id.btnApply))
                .perform(click());
    }
}
