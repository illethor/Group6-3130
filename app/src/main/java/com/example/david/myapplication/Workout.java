package com.example.david.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Workout {
    private String heartrate;
    private String steps;

    public Workout(){

    }

    /**
     *
     * @param heartrate
     * @param steps
     */
    public Workout(String heartrate, String steps){
        this.heartrate = heartrate;
        this.steps = steps;
    }

    @Exclude
    public String getHeartrateList(){return heartrate;}
    @Exclude
    public String getStepsList(){return steps;}
}
