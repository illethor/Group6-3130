package com.example.david.myapplication;


import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class MessageInstrumentedTest {
    // Start the LoginActivity
    @Rule
    public final ActivityTestRule<MessageActivity> mActivityRule =
            new ActivityTestRule<>(MessageActivity.class);

    @Test
    public void checkMessageText(){
        onView(withId(R.id.Message_text)).perform(click());
    }

    @Test
    public void checkMessageSendButton(){
        onView(withId(R.id.Message_sendB)).perform(click());
    }

    /*
    * This test will pass when message sending functionality is implemented.
    * This tests coach to user message.
    * */
    @Test
    public void checkMessageSend(){
        // Create valid login credentials for a dummy account
        String email = "testcoach@test.com";
        String password = "test123";
        // Create message
        String message = "This is a test message";
        // Login as test coach
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password);
        // Type our test message in
        onView(withId(R.id.Message_text)).perform(typeText(message)).perform(closeSoftKeyboard());
        // Click the send button
        onView(withId(R.id.Message_sendB)).perform(click());
        // Verify that success toast message pops up
        onView(withText(R.string.coachMessageSentSuccess)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
