package com.example.david.myapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Workout {
    private ArrayList<Double> heartrate;
    private ArrayList<Double> steps;
    private ArrayList<Double> time;

    public Workout(){

    }

    /**
     * Used to create new workout
     * @param heartrate ArrayList of heartrates gathered a specified times
     * @param steps ArrayList of steps gathered at specified times
     * @param time ArrayList of specified times at which details are polled/gathered.
     **/
    public Workout(ArrayList<Double> heartrate, ArrayList<Double> steps, ArrayList<Double> time){
        this.heartrate = heartrate;
        this.steps = steps;
        this.time = time;
    }

    @Exclude
    public ArrayList<Double> getHeartrateList(){return heartrate;}
    @Exclude
    public ArrayList<Double> getStepsList(){return steps;}
    @Exclude
    public ArrayList<Double> getTimeList(){return time;}
}
