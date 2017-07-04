package com.example.david.myapplication;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test suite for Set Ranges Activity
 */
public class SetRangesUnitTest {

    @Test
    public void tryParsingTest() throws Exception {
        assertFalse(SetRangesActivity.tryParsing("notvalid"));
        assertFalse(SetRangesActivity.tryParsing(null));
        assertFalse(SetRangesActivity.tryParsing("29183192308109238190381309189320324234"));
        assertTrue(SetRangesActivity.tryParsing("1234"));
    }
}
