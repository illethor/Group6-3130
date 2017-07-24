package com.wolkabout.hexiwear.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wolkabout.hexiwear.R;
import com.wolkabout.hexiwear.activity.DatabaseObjects.User;

public class FitnessMainActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseUserReference = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_main);
        setTitle("FitnessWorkout Application: Iteration Two");
        final Button btnMessage = (Button) findViewById(R.id.btnMessage);
        final Button btnGraphs = (Button) findViewById(R.id.graphsBtn);
        final Button btnWorkout = (Button) findViewById(R.id.workoutBtn);

        firebaseUserReference.child(cleanEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User coachUser = dataSnapshot.getValue(User.class);
                if(coachUser.getCoach()){
                    btnMessage.setVisibility(View.VISIBLE);
                    btnGraphs.setVisibility(View.INVISIBLE);
                    btnWorkout.setVisibility(View.INVISIBLE);
                } else {
                    btnMessage.setVisibility(View.INVISIBLE);
                    btnGraphs.setVisibility(View.VISIBLE);
                    btnWorkout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void onDestroy(Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();
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

    /**
     * Removes the dot from an email for database storage
     * */
    public String cleanEmail(String email){
        return email.replaceAll("\\.","");
    }
}
