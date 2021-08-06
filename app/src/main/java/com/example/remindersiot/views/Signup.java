package com.example.remindersiot.views;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.getset.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Signup extends AppCompatActivity {

    private Button btSignup, btLogin;
    private EditText etNama, etEmail, etPassword;
    private FirebaseInit fb = new FirebaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btLogin = findViewById(R.id.btnLogin);
        btSignup = findViewById(R.id.btnSignup);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        User user = new User(nama, email, password);
        fb.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            fb.database.getReference("User").child(fb.mAuth.getCurrentUser().getUid()).child("Akun").setValue(user);
                            fb.database.getReference("User").child(fb.mAuth.getCurrentUser().getUid()).child("Course").child("0").setValue("Course List");
                            fb.database.getReference("User").child(fb.mAuth.getCurrentUser().getUid()).child("Device").child("status").setValue("disconnected");

                            SharedPreferences.Editor editor = getSharedPreferences("iterasiTask", MODE_PRIVATE).edit();
                            editor.putInt("iterasiKe", 1);
                            editor.apply();

                            startActivity(new Intent(Signup.this, Home.class));
                            finish();
                            Toast.makeText(Signup.this, "Authentication Succes", Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(Signup.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}