package com.example.david.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkoutActivity extends AppCompatActivity implements SensorEventListener {

    //firebase objects
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseReference = database.getReference("steps");

    //step counter variables
    TextView tv_stepNum;
    SensorManager sensorManager;
    boolean running = false;
    int stepsTaken = -1;
    String stepsString = "";

    //timer variables
    public TextView timeS;
    public TextView timeM;
    public String stringS="";
    public String stringM="00";
    private Button Start;
    private Button Stop;
    public int counterS = -1;
    public int counterM = 0;
    private boolean stopClicked = true;
    private int uploadCounter = 0;

    /**
     * Main method which sets up timer and step counter and their respective listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Start = (Button) findViewById(R.id.Start);
        Stop = (Button) findViewById(R.id.Stop);
        Stop.setVisibility(View.INVISIBLE);
        timeS = (TextView) findViewById(R.id.textViewS);
        timeM = (TextView) findViewById(R.id.textViewM);

        // Step counter setup
        tv_stepNum = (TextView) findViewById(R.id.tv_stepNum);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //set stop button status
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //change boolean value
                stopClicked=true;

                //if no steps taken, set to 0 (adjust from -1 initialization value)
                if (stepsTaken < 0) {
                    stepsTaken = 0;
                }

                // WRAP UP VALUES HERE
                // PUSH STEP VALUES, HEARTRATE VALUES AND TIME VALUES TO WORKOUT OBJECT HERE!!!!!!!!!

                stepsString += stepsTaken + " ";
                firebaseReference.setValue(stepsString);

                // CLOSE OUT THIS ACTIVITY
                finish();
            }
        });

        Start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                stopClicked=false;
                Start.setVisibility(View.INVISIBLE);
                Stop.setVisibility(View.VISIBLE);

                CountDownTimer startSS = new CountDownTimer(1000000000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        uploadCounter++;

                        //Step counter upload
                        if (uploadCounter % 15 == 0) {
                            //adjust for initialized steps taken value of -1
                            if (stepsTaken < 0) {
                                stepsTaken = 0;
                            }
                            stepsString += stepsTaken + " ";
                            firebaseReference.setValue(stepsString);
                        }

                        if(stopClicked){
                            timeS.setText(stringS);
                            timeM.setText(stringM);
                            this.cancel();
                        }
                        else{
                            RangeOfTime(counterS,counterM);
                        }
                        timeS.setText(stringS);
                        timeM.setText(stringM);
                    }
                    @Override
                    public void onFinish(){
                    }
                }.start();
            }
        });
    }

    /**
     * Method which handles the seconds and minutes displayed base on parameter values.
     * @param S The number of seconds.
     * @param M The number of minutes.
     * */
    public void RangeOfTime(int S, int M){
        if (S < 9) {
            S++;
            stringS = "0" + S;
        }
        else if(S < 59&&S >= 9){
            S++;
            stringS = S + "";
        }
        else{
            M++;
            stringM = "0"+M+"";
            S = 0;
            stringS = "0"+S;
        }
        counterM = M;
        counterS = S;
    }

    /** STEP COUNTER LOGIC**/
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Error: Sensor not found.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Paused step counter listener.
     */
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    /**
     * Updates the step counter upon it being triggered.
     * @param event step counter change event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            if (!stopClicked) {
                stepsTaken++;
                tv_stepNum.setText(String.valueOf(stepsTaken));
            }
        }
    }

    /**
     * Change of accuracy listener, do nothing in this case.
     * @param sensor step counter sensor
     * @param accuracy accuracy value
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing.
    }
}
