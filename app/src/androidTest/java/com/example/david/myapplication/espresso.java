package com.example.david.myapplication;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
public class espresso {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void checkFunc()

    {

        onView(withId(R.id.button2)).perform(click());

        onView(withId(R.id.Start)).perform(click());

        onView(withId(R.id.Stop)).perform(click());

        onView(withId(R.id.Reset)).perform(click());

        onView(withId(R.id.Return)).perform(click());

    }

}
