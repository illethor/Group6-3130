package com.example.david.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_stepNum;

    SensorManager sensorManager;

    boolean running = false;

    int stepsTaken = -1; // Start at -1 because it seems that the boot up of the sensor triggers
                         // the step counter event.
    /**
     * Upon activity creation will attempt to connect to the sensor service for step tracking.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        tv_stepNum = (TextView) findViewById(R.id.tv_stepNum);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
