package com.wolkabout.hexiwear;

import com.jjoe64.graphview.series.DataPoint;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by David on 2017-07-03.
 */

public class FitnessGraphUnitTest {
    public DataPoint[] generateData(int[] time, int[] steps){
        DataPoint[] db = new DataPoint[steps.length];
        for(int i=0; i<steps.length;i++){
            DataPoint v = new DataPoint(time[i],steps[i]);
            db[i] = v;
        }
        return db;
    }
}
