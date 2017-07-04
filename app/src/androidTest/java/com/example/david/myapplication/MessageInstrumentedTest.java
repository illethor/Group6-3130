package com.example.david.myapplication;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

public class MessageInstrumentedTest {
    // Start the LoginActivity
    @Rule
    public final ActivityTestRule<MessageActivity> mActivityRule =
            new ActivityTestRule<>(MessageActivity.class);
}
