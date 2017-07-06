package com.example.david.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Workout {
    public String heartrate;
    public String steps;
    public String workoutType;
    public int averageHeartrate;

    public Workout(){

    }
    public Workout(String heartrate, String steps, String workoutType, int averageHeartrate){
        this.heartrate = heartrate;
        this.steps = steps;
        this.workoutType = workoutType;
        this.averageHeartrate = averageHeartrate;
    }
    @Exclude
    public String getHeartrateList(){return heartrate;}
    @Exclude
    public String getStepsList(){return steps;}
    @Exclude
    public String getWorkoutType(){return workoutType;}
    @Exclude
    public int getAverageHeartrate(){return averageHeartrate;}
}
