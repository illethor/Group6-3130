
//FitnessGraphActivity activity tests

package com.wolkabout.hexiwear;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessGraphActivity;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;
import com.google.firebase.auth.FirebaseAuth;
import com.wolkabout.hexiwear.activity.FitnessLoginActivity;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;



public class FitnessGraphInstrumentedTest {
    //Start graph activity
    /*@Rule
    public final ActivityTestRule<FitnessGraphActivity> mActivityRule =
            new ActivityTestRule<>(FitnessGraphActivity.class);*/

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
        onView(withId(R.id.graphsBtn)).perform(click());
    }

    @After
    public final void signoutAfter() {
        FirebaseAuth.getInstance().signOut();
        SystemClock.sleep(2000);
    }

    @AfterClass
    public static final void signoutAfterClass() {
        FirebaseAuth.getInstance().signOut();
    }
    @Test
    public void stepsButtonTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.stepGraphBtn))
                .perform(click());
    }
    @Test
    public void heartrateButtonTest() throws Exception{
        SystemClock.sleep(4000);
        //Checks the button is there by clicking it
        onView(withId(R.id.heartrateGraphBtn))
                .check(matches(isDisplayed()));
    }
    @Test
    public void averageHeartrateTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.averageHeartrates))
                .perform(click());
    }
    @Test
    public void averageStepsTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.averageSteps))
                .perform(click());
    }
    @Test
    public void exportTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.exportBtn))
                .perform(click());
    }
    //Checks if specified string is one of the spinner's selections
    @Test
    public void isInList(){
        //Opens spinner view
        onView(withId(R.id.graphs_spinner))
                .perform(click());

        SystemClock.sleep(2000);
        //Attempts to click whatever spinner option starts with specified string
        onData(hasToString(startsWith("workout 2")))
                .perform(click());
    }
}
