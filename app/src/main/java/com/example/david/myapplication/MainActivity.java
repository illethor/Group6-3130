package com.example.david.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button stepBtn = (Button)findViewById(R.id.stepCounterBtn);

        stepBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, StepCounterActivity.class));
            }
        });


        Button Alert = (Button)findViewById(R.id.button3);

        Alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AlertActivity.class));
            }
        });

    }

    /**
     * Method which switches our view to the timer activity upon button press.
     * */
    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }

    /**
     * Method which sends us to the graph activity when button is pressed.
     * */
    public void toGraphs(View v) {
        startActivity(new Intent(MainActivity.this, GraphActivity.class));
    }

    /**
     * Method which sends us to the set ranges activity when button is pressed.
     * */
    public void toSetRanges(View view){
        Intent intent = new Intent(MainActivity.this, SetRangesActivity.class);
        startActivity(intent);
    }
}
