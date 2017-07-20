package com.wolkabout.hexiwear;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessLoginActivity;
import com.wolkabout.hexiwear.activity.FitnessSetRangesActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import com.google.firebase.auth.FirebaseAuth;

public class FitnessSetRangesInstrumentedTest {

    @Rule
    public final ActivityTestRule<FitnessLoginActivity> mActivityRule =
            new ActivityTestRule<>(FitnessLoginActivity.class);

    @Before
    public final void setupLogin() {
        String email = "testathlete@test.com";
        String password = "test123";

        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        // Create a wait to avoid any race conditions
        SystemClock.sleep(2000);
    }

    @After
    public final void signoutAfter() {
        FirebaseAuth.getInstance().signOut();
    }

    @AfterClass
    public static final void signoutAfterClass() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Tests for UI elements present on page
     */
    @Test
    public void testUI() {
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.tvSelection))
                .check(matches(withText("Select your workout type:")));

        SystemClock.sleep(2000);

        onView(withId(R.id.tvLowerBound))
                .check(matches(withText("Heartrate Lower Bound:")));

        SystemClock.sleep(2000);

        onView(withId(R.id.tvUpperBound))
                .check(matches(withText("Heartrate Upper Bound:")));
    }

    /**
     * Test spinner for expected workout type values.
     */
    @Test
    public void testSpinner() {
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.spnInfo))
                .perform(click());

        SystemClock.sleep(2000);

        onData(hasToString(startsWith("Walking")))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.spnInfo))
                .check(matches(withSpinnerText(containsString("Walking"))));

        SystemClock.sleep(2000);

        onView(withId(R.id.spnInfo))
                .perform(click());

        SystemClock.sleep(2000);

        onData(hasToString(startsWith("Running")))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.spnInfo))
                .check(matches(withSpinnerText(containsString("Running"))));
    }

    /**
     * Test lower bound edit text field in UI
     */
    @Test
    public void testLBEditTextRanges() {
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.etLowerBound))
                .perform(click())
                .perform(typeText("123sadsd4"))
                .check(matches(withText("1234")))
                .perform(closeSoftKeyboard());
    }

    /**
     * Test upper bound edit text field in UI
     */
    @Test
    public void testUBEditTextRanges() {
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.etUpperBound))
                .perform(click())
                .perform(typeText("123sadsd4"))
                .check(matches(withText("1234")))
                .perform(closeSoftKeyboard());

    }

    /**
     * Test upper bound edit text field in UI
     */
    @Test
    public void testApplyButton() {
        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        //Apply
        onView(withId(R.id.btnApply))
                .perform(click());
    }
}
