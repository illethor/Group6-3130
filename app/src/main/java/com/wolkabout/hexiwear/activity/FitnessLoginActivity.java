package com.wolkabout.hexiwear.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.wolkabout.hexiwear.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FitnessLoginActivity extends AppCompatActivity {

    // Define the Firebase authentication and listener objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    // Declare variables
    private TextView txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnSignup;
    private TextView txtStatusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_login);

        setTitle("Fitness Workout Application");

        // Initialize all firebase auth information
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // If user is signed in simply send them to main activity
                    startActivity(new Intent(FitnessLoginActivity.this, FitnessMainActivity.class));
                } else {
                    // User is signed out
                    Log.d("User Signed Out", "onAuthStateChanged:signed_out");
                }
            }
        };

        // Find required buttons and text fields
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtStatusMessage = (TextView) findViewById(R.id.txtStatusMessage);

        // Create an onClick listener for btnLogin
        // When clicked attempt to sign in
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // If email and password have text attempt log in
                if(!TextUtils.isEmpty(txtEmail.getText()) && !TextUtils.isEmpty(txtPassword.getText())) {
                    startLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
                } else {
                    if(TextUtils.isEmpty(txtEmail.getText())) {
                        txtStatusMessage.setText(R.string.emailEmpty);
                    } else {
                        txtStatusMessage.setText(R.string.passwordEmpty);
                    }
                }
            }
        });

        // Create an onClick listener for btnSignup
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(FitnessLoginActivity.this, FitnessRegisterActivity.class));
            }
        });
    }

    public void startLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if (!task.isSuccessful()){
                    txtStatusMessage.setText(R.string.failedLogin);
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
