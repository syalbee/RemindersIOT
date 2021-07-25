package com.example.remindersiot.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddCourse extends AppCompatActivity {

    private FirebaseInit fb = new FirebaseInit();
    private Button btAdd;
    private ImageButton btBack;
    private EditText etCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        btAdd = findViewById(R.id.btAddcourse);
        btBack = findViewById(R.id.Ibtbackcourse);
        etCourse = findViewById(R.id.etCourse);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourse.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKeFirebase();
            }
        });
    }

    private  void simpanKeFirebase(){
        String course = etCourse.getText().toString();

        fb.mDatabaseRef.child(fb.mAuth.getUid()).child("Course").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              long w = snapshot.getChildrenCount();
                fb.database.getReference("User").child(fb.mAuth.getUid()).child("Course").child(String.valueOf(w)).setValue(course);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        etCourse.setText("");
        Toast.makeText(AddCourse.this, "Succes add course" , Toast.LENGTH_SHORT).show();
    }


}