package com.example.david.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
//import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Workout Application: Iteration Two");
    }

    /**
     * Method which switches our view to the timer activity upon button press.
     * */
    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
        startActivity(intent);
    }

    /**
     * Method for signing out
     * */
    public void signOut(View view){
        if(FirebaseAuth.getInstance() != null){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
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

    public void toMessaging(View view){
        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
        startActivity(intent);
    }
}
