package com.example.david.myapplication;

/**
 * Created by Wenlong on 2017/6/8.
 */

import java.util.Timer;
import java.util.TimerTask;
public class timerTask{
    String message;
    int secondsPassed = 0;

    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
        public void run(){
            secondsPassed++;
            message = "Seconds passed: "+1;
        }
    };
    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    /*public static void main(String[] args){
        timer timer1 = new timer();
        timer1.start();
    }*/
}

