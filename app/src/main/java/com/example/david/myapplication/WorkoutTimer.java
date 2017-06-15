package com.example.david.myapplication;

/**
 * Created by Wenlong on 2017/6/8.
 */

import java.util.Timer;
import java.util.TimerTask;

public class WorkoutTimer {
    String message;
    int secondsPassed = 0;

    java.util.Timer timer = new java.util.Timer();
    java.util.TimerTask task = new java.util.TimerTask(){
        public void run(){
            secondsPassed++;
            message = "Seconds passed: "+1;
        }
    };
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    /*public static void main(String[] args){
        TimerActivity timer1 = new TimerActivity();
        timer1.start();
    }*/
}

