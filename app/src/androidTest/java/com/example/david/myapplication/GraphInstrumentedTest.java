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
    //Start graph activity
    @Rule
    public final ActivityTestRule<GraphActivity> mActivityRule =
            new ActivityTestRule<>(GraphActivity.class);

    //Tests to make sure all buttons are clickable
    @Test
    public void stepsButtonTest() throws Exception{
        onView(withId(R.id.stepGraphBtn)).perform(click());
    }
    @Test
    public void heartrateButtonTest() throws Exception{
        onView(withId(R.id.heartrateGraphBtn)).perform(click());
    }
    @Test
    public void heartrateAverageButtonTest() throws Exception{
        onView(withId(R.id.averageHeartrates)).perform(click());
    }
    @Test
    public void stepsAverageButtonTest() throws Exception{
        onView(withId(R.id.averageSteps)).perform(click());
    }


    //Checks if specified workouts' names are in the spinner's selections
    //and presses button to graph the steps of the workouts one after the other
    @Test
    public void isInList(){
        //Opens spinner view
        onView(withId(R.id.graphs_spinner)).perform(click());
        //Attempts to click whatever spinner option starts with specified string
        onData(hasToString(startsWith("workout 2"))).perform(click());
        onView(withId(R.id.stepGraphBtn)).perform(click());
        onView(withId(R.id.heartrateGraphBtn)).perform(click());
        onView(withId(R.id.graphs_spinner)).perform(click());
        onData(hasToString(startsWith("workout 1"))).perform(click());
        onView(withId(R.id.stepGraphBtn)).perform(click());
        onView(withId(R.id.heartrateGraphBtn)).perform(click());
    }

}

