package com.example.david.myapplication;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class LoginTests {
    // Messages
    String FAILED_LOGIN_MESSAGE = "Incorrect Login Information";

    // Start the LoginActivity
    @Rule
    public final ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    // Begin verifying all buttons and text fields are displaying and clickable
    @Test
    public void checkEmailField(){
        onView(withId(R.id.txtEmail)).perform(click());
    }
    @Test
    public void checkPasswordField(){
        onView(withId(R.id.txtPassword)).perform(click());
    }
    @Test
    public void checkLoginBtn(){
        onView(withId(R.id.btnLogin)).perform(click());
    }
    @Test
    public void checkSignupBtn(){
        onView(withId(R.id.btnSignup)).perform(click());
    }

    // Verify login is functioning -- When all these tests pass login is working properly
    @Test
    public void failedLoginTest(){
        // Create invalid login credentials that will fail
        String email = "badEmail@email.com";
        String password = "badPassword";
        // Attempt to login with them
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());;
        onView(withId(R.id.btnLogin)).perform(click());
        // Verify status message is displaying "Incorrect Login Information"
        onView(withId(R.id.txtStatusMessage)).check(matches(withText(FAILED_LOGIN_MESSAGE)));
    }
    @Test
    public void testSucessfulLogin(){
        // Create valid login credentials for a dummy account
        String email = "test@testuser.com";
        String password = "testuser";
        // Attempt to login with them
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());;
        onView(withId(R.id.btnLogin)).perform(click());
        // Verify the activity switched to main activity which will only happen after successful login
        intended(hasComponent(MainActivity.class.getName()));
    }

    // Verify sign up is functioning -- When all these below tests pass signup is working properly
    @Test
    public void testSignupBtnPress(){
        // Press signup btn
        onView(withId(R.id.btnSignup)).perform(click());
        // Verify the activity has switched to the register activity below
        intended(hasComponent(RegisterActivity.class.getName()));
    }

}
