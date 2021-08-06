package com.example.remindersiot.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private Button btLogin;
    private EditText etEmail, etPassword;
    private FirebaseInit db = new FirebaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btnSignin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPass);

       btLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               logIn();
           }
       });
    }

    //fungsi signin untuk mengkonfirmasi data pengguna yang sudah mendaftar sebelumnya
    private void logIn() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        db.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "logIn:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            setUlang();
                            startActivity(new Intent(Login.this, Home.class));
                            finish();

                        } else {
                            Toast.makeText(Login.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void setUlang(){


        db.database.getReference().child("User").child(db.mAuth.getUid()).child("TaskDevice").child("jumlahIterasi")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String count =(String) snapshot.getValue();
                        int iterasi = Integer.parseInt(count);

                        SharedPreferences.Editor editor = getSharedPreferences("iterasiTask", MODE_PRIVATE).edit();
                        editor.putInt("iterasiKe", iterasi);
                        editor.apply();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}