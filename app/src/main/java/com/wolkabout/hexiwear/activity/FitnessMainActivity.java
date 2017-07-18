package com.wolkabout.hexiwear.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.wolkabout.hexiwear.R;

public class FitnessMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_main);
        setTitle("FitnessWorkout Application: Iteration Two");
    }

    /**
     * Method which switches our view to the timer activity upon button press.
     * */
    public void sendMessage(View view) {
        Intent intent = new Intent(FitnessMainActivity.this, FitnessWorkoutActivity.class);
        startActivity(intent);
    }

    /**
     * Method for signing out
     * */
    public void signOut(View view){
        if(FirebaseAuth.getInstance() != null){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(FitnessMainActivity.this, FitnessLoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Method which sends us to the graph activity when button is pressed.
     * */
    public void toGraphs(View v) {
        startActivity(new Intent(FitnessMainActivity.this, FitnessGraphActivity.class));
    }

    /**
     * Method which sends us to the set ranges activity when button is pressed.
     * */
    public void toSetRanges(View view){
        Intent intent = new Intent(FitnessMainActivity.this, FitnessSetRangesActivity.class);
        startActivity(intent);
    }

    public void toMessaging(View view){
        Intent intent = new Intent(FitnessMainActivity.this, FitnessMessageActivity.class);
        startActivity(intent);
    }
}
