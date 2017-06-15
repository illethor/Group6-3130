//GraphActivity activity tests

package com.example.david.myapplication;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;



public class GraphInstrumentedTest {

    /**
     * Matches an item from an AdapterView with a specific String.
     * (The items in AdapterView should be strings)
     */

    //Start graph activity
    @Rule
    public final ActivityTestRule<GraphActivity> mActivityRule =
            new ActivityTestRule<>(GraphActivity.class);

    @Test
    public void stepsButtonTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.steps))
                .perform(click());
    }
    @Test
    public void heartrateButtonTest() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.heartrate))
                .perform(click());
    }

    //Checks if specified string is one of the spinner's selections
    @Test
    public void isInList(){
        //Opens spinner view
        onView(withId(R.id.graphs_spinner))
                .perform(click());
        //Attempts to click whatever spinner option starts with specified string
        onData(hasToString(startsWith("Workout 3")))
                .perform(click());
    }
}
