package com.example.david.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlertActivity extends AppCompatActivity {

    final Context context = this;
    private Button button;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Heartrate Alert!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Heartrate has fallen outside of specified range!").setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        AlertActivity.this.finish();
                    }
                });

        // create alert dialog and heartrate variables for testing
        AlertDialog alertDialog = alertDialogBuilder.create();
        int rate = 160;
        int upperB = 150;
        int lowerB = 75;


        // show the alert if heartrate falls outside specified bounds
        if(rate > upperB || rate < lowerB)
            alertDialog.show();
    }
}


