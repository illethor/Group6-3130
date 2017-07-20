package com.wolkabout.hexiwear;

import com.jjoe64.graphview.series.DataPoint;
import com.wolkabout.hexiwear.activity.FitnessGraphActivity;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by David on 2017-07-03.
 */

public class FitnessGraphUnitTest {
    @Test
    public void testGenerateData(){
        int[] xArray = new int[2];
        int[] yArray = new int[2];
        xArray[0] = 0;xArray[1] = 1;
        yArray[0] = 0;yArray[1] = 1;

        DataPoint[] db = FitnessGraphActivity.generateData(xArray,yArray);

        DataPoint v = new DataPoint(0,0);
        DataPoint w = new DataPoint(1,1);
        DataPoint[] array = new DataPoint[2];
        array[0] = v; array[1] = w;
        assertEquals(v.getX(), db[0].getX());
        assertEquals(v.getY(), db[0].getY());
    }
}