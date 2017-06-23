package com.example.david.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.myapplication.DatabaseObjects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    // User already registered flagged
    private Boolean alreadyRegistered = false;

    // Define the Firebase authentication and listener objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // Declare variables
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtCoachEmail;
    private Button btnRegister;
    private DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize all firebase auth information
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // If user is signed in simply send them to main activity
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    // User is signed out
                    Log.d("User Signed Out", "onAuthStateChanged:signed_out");
                }
            }
        };

        // Find all buttons and text fields
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtCoachEmail = (EditText) findViewById(R.id.txtCoachEmail);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        // Connect to users database
        databaseArtists = FirebaseDatabase.getInstance().getReference("users");

        // Create an onClick for btnRegister
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Check all required cases to ensure registration is valid
                // Check if email is empty
                // Check if password is empty
                // Check if password is greater than or equal to 6 characters
                // Check if coach email is a valid entry in database
                // Check if coach email is actually a coach
                if (TextUtils.isEmpty(txtEmail.getText())){
                    Toast.makeText(RegisterActivity.this, R.string.emailEmpty, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txtPassword.getText())){
                    Toast.makeText(RegisterActivity.this, R.string.passwordEmpty, Toast.LENGTH_SHORT).show();
                } else if (txtPassword.getText().toString().length() <= 5){
                    Toast.makeText(RegisterActivity.this, R.string.passwordTooSmall, Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(txtCoachEmail.getText()) ){
                    // If we enter here we can be sure the coach email is not empty and the coach email was found in users DB
                    // now verify that coach email is really a coach and not an athlete
                    databaseArtists.child(cleanEmail(txtCoachEmail)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User objUser = dataSnapshot.getValue(User.class);
                            // Check if user is found
                            if(!alreadyRegistered) {
                                if (objUser == null) {
                                    Toast.makeText(RegisterActivity.this, R.string.coachEmailDNE, Toast.LENGTH_SHORT).show();
                                } else if (!objUser.getCoach()) {
                                    // Coach email is a valid user email however it's not a coach
                                    Toast.makeText(RegisterActivity.this, R.string.userNotACoach, Toast.LENGTH_SHORT).show();
                                } else if (!objUser.getAtheleteEmail().equals("")) {
                                    // Coach is already assigned to a user
                                    Toast.makeText(RegisterActivity.this, R.string.coachHasAthelete, Toast.LENGTH_SHORT).show();
                                } else {
                                    registerUser();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                } else {
                    if(!alreadyRegistered) registerUser();
                }
            }
        });
    }

    /**
     * Method for registering a user
     * */
    public void registerUser(){
        // Create a user using firebase auth
        mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, R.string.registrationFailed, Toast.LENGTH_SHORT).show();
                        } else {
                            User objUser;
                            if(TextUtils.isEmpty(txtCoachEmail.getText())){
                                // If user is a coach
                                objUser = new User(true, txtEmail.getText().toString());
                            } else {
                                // If user is an athlete
                                // Create the user object
                                objUser = new User(false, txtCoachEmail.getText().toString(), txtEmail.getText().toString());
                                // Assign specified coach to registering user
                                databaseArtists.child(cleanEmail(txtCoachEmail)).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User coach = dataSnapshot.getValue(User.class);
                                        coach.setAthleteEmail(txtEmail.getText().toString());
                                        databaseArtists.child(cleanEmail(txtCoachEmail)).setValue(coach);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            // Store user in fire base database
                            databaseArtists.child(cleanEmail(txtEmail)).setValue(objUser);
                            alreadyRegistered = true;
                        }
                    }
                });
    }

    /**
     * Removes the dot from an email for database storage
     * */
    public String cleanEmail(EditText email){
        return email.getText().toString().replaceFirst("\\.","");
    }
}
