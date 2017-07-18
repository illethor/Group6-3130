package com.wolkabout.hexiwear.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wolkabout.hexiwear.activity.DatabaseObjects.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wolkabout.hexiwear.R;

public class FitnessMessageActivity extends AppCompatActivity {
    // Define the Firebase authentication and listener objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference firebaseUserReference = database.getReference("users");
    private DatabaseReference AthleteUserReference = database.getReference("users");


    //initial all variables
    private TextView tvMessageSymbol;
    private TextView tvMessageSendTo;
    private TextView tvMessageReceiver;
    private Button  btnMessageSendB;
    private EditText etMessageText;

    //variables
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_message);
        // Initialize all firebase auth information


        // Hook up buttons / txt fields
        tvMessageSymbol = (TextView) findViewById(R.id.Message_symbol);
        tvMessageSendTo = (TextView) findViewById(R.id.Message_sendTo);
        tvMessageReceiver = (TextView) findViewById(R.id.Message_receiver);
        btnMessageSendB = (Button) findViewById(R.id.Message_sendB);
        etMessageText = (EditText) findViewById(R.id.Message_text);

        // Get the coaches user object
        mAuth = FirebaseAuth.getInstance();
        userEmail = mAuth.getCurrentUser().getEmail();

        // Add a listener for if a coach has a messageNotification
        firebaseUserReference.child(cleanEmail(userEmail)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User coachUser = dataSnapshot.getValue(User.class);
                tvMessageReceiver.setText(coachUser.getAtheleteEmail());
                if(coachUser.getMessageNotification()){
                    // Display toast confirmation
                    Toast.makeText(FitnessMessageActivity.this, "Athlete has read message", Toast.LENGTH_SHORT).show();
                    // Unset notification flag
                    coachUser.setMessageNotification(false);
                    firebaseUserReference.child(cleanEmail(userEmail)).setValue(coachUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Add listener to send message button for sending message to a coaches athlete
        btnMessageSendB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                firebaseUserReference.child(cleanEmail(userEmail)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final User coachUser = dataSnapshot.getValue(User.class);
                        // set the coaches athlete message to the input message
                        if(coachUser.getAtheleteEmail() != null && coachUser.getAtheleteEmail().length() > 0){
                            AthleteUserReference.child(cleanEmail(coachUser.getAtheleteEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User athleteUser = dataSnapshot.getValue(User.class);
                                    athleteUser.setMessage(etMessageText.getText().toString());
                                    AthleteUserReference.child(cleanEmail(coachUser.getAtheleteEmail())).setValue(athleteUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        // if message was completed successfully display success message and wipe text message
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(FitnessMessageActivity.this, R.string.coachMessageSentSuccess, Toast.LENGTH_SHORT).show();
                                            etMessageText.setText("");
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    /**
     * Removes the dot from an email for database storage
     * */
    public String cleanEmail(String email){
        return email.replaceAll("\\.","");
    }
}
