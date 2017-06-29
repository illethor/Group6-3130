package com.example.david.myapplication;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by David on 2017-06-01.
 */

public class MainInstrumentedTest {

    //Start graph activity
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void unlockScreen() {
        final Activity activity = mActivityRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void button1Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.button))
                .perform(click());
    }

    @Test
    public void button2Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.button2))
                .perform(click());
    }

    @Test
    public void button3Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.button3))
                .perform(click());
    }

    @Test
    public void button4Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.button4))
                .perform(click());
    }

    @Test
    public void button5Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.button5))
                .perform(click());
    }

    @Test
    public void button6Test() throws Exception{
        //Checks the button is there by clicking it
        onView(withId(R.id.stepCounterBtn))
                .perform(click());
    }
}
