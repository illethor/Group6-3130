package com.example.david.myapplication;

import android.os.SystemClock;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class RegisterTests {
    @Rule
    public final ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);
    // Verify all buttons and fields are there by simply clicking on them
    @Test
    public void testEmailField(){
        onView(withId(R.id.txtEmail)).perform(click());
    }
    @Test
    public void testPasswordField(){
        onView(withId(R.id.txtPassword)).perform(click());
    }
    @Test
    public void testCoachEmailField(){
        onView(withId(R.id.txtCoachEmail)).perform(click());
    }
    @Test
    public void testRegisterButton(){
        onView(withId(R.id.btnRegister)).perform(click());
    }
    // All tests for pressing register is below once working register can be considered functional
    @Test
    public void testNoEmail(){
        onView(withId(R.id.btnRegister)).perform(click());
        // wait to avoid race condition
        SystemClock.sleep(1500);
        // Check when button pressed with invalid credentials a Toast message is displayed
        onView(withText(R.string.emailEmpty)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    @Test
    public void testNoPassword(){
        String email = "badEmail@email.com";
        // type email and press register button
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.btnRegister)).perform(click());
        // verify no password toast message has appeared on screen
        onView(withText(R.string.passwordEmpty)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    @Test
    public void testPasswordTooSmall(){
        String email = "testEmail@email.com";
        String password = "12345";
        // type email and password
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        // perform click, wait for race condiotion, verify toast msg has appeared
        onView(withId(R.id.btnRegister)).perform(click());
        SystemClock.sleep(1500);
        onView(withText(R.string.passwordTooSmall)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
