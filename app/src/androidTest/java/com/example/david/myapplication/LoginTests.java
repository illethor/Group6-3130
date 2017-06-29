package com.example.david.myapplication;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.AfterClass;
import org.junit.Before;
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

public class LoginTests {
    // Start the LoginActivity
    @Rule
    public final ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

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
    // Verify correct error messages are displaying with blank email & password
    @Test
    public void noEmailEntered(){
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.txtStatusMessage)).check(matches(withText(R.string.emailEmpty)));
    }
    @Test
    public void noPasswordEntered(){
        // Dummy email variable
        String email = "badEmail@email.com";
        // Set email so password verification fails
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        // Attempt to sign in
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.txtStatusMessage)).check(matches(withText(R.string.passwordEmpty)));
    }
    // Verify login is functioning -- When all these tests pass login is working properly
    @Test
    public void failedLoginTest(){
        // Create invalid login credentials that will fail
        String email = "badEmail@email.com";
        String password = "badPawefssword";
        // Attempt to login with them
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        // Avoid any race condition errors by waiting to confirm log in attempt happens
        SystemClock.sleep(1500);
        // Verify status message is displaying "Incorrect Login Information"
        onView(withId(R.id.txtStatusMessage)).check(matches(withText(R.string.failedLogin)));
    }
    @Test
    public void testSuccessfulLogin(){
        // Create valid login credentials for a dummy account
        String email = "test@testuser.com";
        String password = "testuser";
        // Attempt to login with them
        onView(withId(R.id.txtEmail)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
        // Create a wait to avoid any race conditions
        SystemClock.sleep(1500);
        // Check if we have a currentUser if we do we have successfully signed in
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            fail();
        } else {
            // If we sign in successfully make sure to end session and go back to main screen
            FirebaseAuth.getInstance().signOut();
        }
    }
    // Clean-up after all tests have completed
    @AfterClass
    public static void tearDown(){
        FirebaseAuth.getInstance().signOut();
    }

}
