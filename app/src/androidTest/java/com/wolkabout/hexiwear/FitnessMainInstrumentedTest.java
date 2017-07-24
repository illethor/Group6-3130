package com.wolkabout.hexiwear;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.wolkabout.hexiwear.activity.FitnessLoginActivity;
import com.wolkabout.hexiwear.activity.FitnessMainActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by David on 2017-06-01.
 */

public class FitnessMainInstrumentedTest {

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
        SystemClock.sleep(4000);
    }

    @After
    public final void signoutAfter() {
        FirebaseAuth.getInstance().signOut();
    }

    @AfterClass
    public static final void signoutAfterClass() {
        FirebaseAuth.getInstance().signOut();
    }

    // Test Graph Button
    @Test
    public void graphTest() throws Exception{
        SystemClock.sleep(1500);
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
