package com.example.david.myapplication;

import android.content.Context;
import android.content.Intent;
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
import 	android.os.CountDownTimer;
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
    int stepsTaken = -1; // Start at -1 because it seems that the boot up of the sensor triggers
    // the step counter event.

    //timer variables
    public TextView timeS;
    public TextView timeM;
    public String stringS="";
    public String stringM="00";
    private Button Start;
    private Button Stop;
    public int counterS = -1;
    public int counterM = 0;
    private boolean stopClicked = false;
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
                firebaseReference.setValue(stepsTaken);

                //change boolean value
                stopClicked=true;
                Start.setVisibility(View.VISIBLE);
                Stop.setVisibility(View.INVISIBLE);
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
    public void sendMessageReset(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        finish();
        startActivity(intent);
    }
    public void sendMessageReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

    /**
     * When resumed will attempt to reconnect to the sensor to get data.
     * */
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

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            stepsTaken++;
            tv_stepNum.setText(String.valueOf(stepsTaken));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing.
    }
}
