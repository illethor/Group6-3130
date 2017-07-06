package com.example.david.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MessageActivity extends AppCompatActivity {
    // Define the Firebase authentication and listener objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //initial all variables
    private TextView tvMessageSymbol;
    private TextView tvMessageSendTo;
    private TextView tvMessageReceiver;
    private Button  btnMessageSendB;
    private EditText etMessageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        // Initialize all firebase auth information
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // If user is signed in simply send them to main activity
                    startActivity(new Intent(MessageActivity.this, LoginActivity.class));
                }
            }
        };
    }
}
