package com.example.david.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import 	android.os.CountDownTimer;

public class timer extends AppCompatActivity {
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

        //set stop button status
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
        Intent intent = new Intent(this, timer.class);
        startActivity(intent);
    }
    public void sendMessageReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
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
}
