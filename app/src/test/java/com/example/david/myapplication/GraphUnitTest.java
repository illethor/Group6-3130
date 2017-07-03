package com.example.david.myapplication;

import com.jjoe64.graphview.series.DataPoint;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by David on 2017-07-03.
 */

public class GraphUnitTest {
    public DataPoint[] generateData(int[] time, int[] steps){
        DataPoint[] db = new DataPoint[steps.length];
        for(int i=0; i<steps.length;i++){
            DataPoint v = new DataPoint(time[i],steps[i]);
            db[i] = v;
        }
        return db;
    }

    @Test
    public void testGenerateData(){
        int[] xArray = new int[2];
        int[] yArray = new int[2];
        xArray[0] = 0;xArray[1] = 1;
        yArray[0] = 0;yArray[1] = 1;

        DataPoint v = new DataPoint(0,0);
        DataPoint w = new DataPoint(1,1);
        DataPoint[] array = new DataPoint[2];
        array[0] = v; array[1] = w;
        assertArrayEquals(generateData(xArray,yArray), array);

    }
}
