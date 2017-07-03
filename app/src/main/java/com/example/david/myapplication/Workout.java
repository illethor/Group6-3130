package com.example.david.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Workout {
    public String heartrate;
    public String steps;

    public Workout(){

    }
    public Workout(String heartrate, String steps){
        this.heartrate = heartrate;
        this.steps = steps;
    }

    @Exclude
    public String getHeartrateList(){return heartrate;}
    @Exclude
    public String getStepsList(){return steps;}
}
