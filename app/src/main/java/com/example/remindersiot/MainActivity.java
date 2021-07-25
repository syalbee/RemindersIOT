package com.example.remindersiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.views.Home;
import com.example.remindersiot.views.Login;
import com.example.remindersiot.views.Signup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private Button btnStart;

    private FirebaseInit db = new FirebaseInit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, Worldi!");

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        db.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Toast.makeText(MainActivity.this, "Masuk main activity", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        db.mAuth.addAuthStateListener(db.mAuthListener);
    }
}