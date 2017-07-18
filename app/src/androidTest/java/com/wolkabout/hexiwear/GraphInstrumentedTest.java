
//FitnessGraphActivity activity tests

package com.wolkabout.hexiwear;

import android.support.test.rule.ActivityTestRule;

import com.wolkabout.hexiwear.activity.FitnessGraphActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;


public class GraphInstrumentedTest {

    /**
     * Matches an item from an AdapterView with a specific String.
     * (The items in AdapterView should be strings)
     */


    //Start graph activity
    @Rule
    public final ActivityTestRule<FitnessGraphActivity> mActivityRule =
            new ActivityTestRule<>(FitnessGraphActivity.class);

    @Test
    public void stepsButtonTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.stepGraphBtn))
                .perform(click());
    }
    @Test
    public void heartrateButtonTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.heartrateGraphBtn))
                .perform(click());
    }

    //Checks if specified string is one of the spinner's selections
    @Test
    public void isInList(){
        //Opens spinner view
        onView(withId(R.id.graphs_spinner))
                .perform(click());
        //Attempts to click whatever spinner option starts with specified string
        onData(hasToString(startsWith("FitnessWorkout 3")))
                .perform(click());
    }
}
