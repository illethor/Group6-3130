package com.wolkabout.hexiwear;

import android.os.SystemClock;
import android.provider.Settings;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.wolkabout.hexiwear.activity.FitnessLoginActivity;
import com.wolkabout.hexiwear.activity.FitnessWorkoutActivity;
import com.wolkabout.hexiwear.activity.LoginActivity;
import com.wolkabout.hexiwear.activity.MainActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class FitnessWorkoutInstrumentedTest {

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

        //Checks the button is there by clicking it
        onView(withId(R.id.workoutBtn))
                .perform(click());

        SystemClock.sleep(2000);

        //Spinner
        onView(withId(R.id.spnInfo))
                .perform(click());
        onData(hasToString(startsWith("Walking")))
                .perform(click());

        SystemClock.sleep(2000);

        //Bounds
        onView(withId(R.id.etLowerBound))
                .perform(click())
                .perform(typeText("50"));

        SystemClock.sleep(200);

        pressBack();

        SystemClock.sleep(2000);

        onView(withId(R.id.etUpperBound))
                .perform(click())
                .perform(typeText("150"));

        pressBack();

        SystemClock.sleep(2000);

        //Apply
        onView(withId(R.id.btnApply))
                .perform(click());

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

        SystemClock.sleep(2000);

        onView(withId(R.id.Stop))
                .perform(click());
    }
}
