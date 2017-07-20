package com.wolkabout.hexiwear;

import com.wolkabout.hexiwear.activity.FitnessSetRangesActivity;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Unit test suite for Set Ranges Activity
 */
public class FitnessSetRangesUnitTest {
    @Test
    public void tryParsingTest() throws Exception {
        assertFalse(FitnessSetRangesActivity.tryParsing("notvalid"));
        assertFalse(FitnessSetRangesActivity.tryParsing(null));
        assertFalse(FitnessSetRangesActivity.tryParsing("29183192308109238190381309189320324234"));
        assertTrue(FitnessSetRangesActivity.tryParsing("1234"));
    }
}
