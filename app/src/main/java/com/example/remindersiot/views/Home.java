package com.example.remindersiot.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.adapter.TaskAdapter;
import com.example.remindersiot.data.GetDate;
import com.example.remindersiot.data.Task;
import com.example.remindersiot.database.FirebaseInit;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    private FloatingActionButton btMenu, btAdd, btAddcourse;
    private ImageButton btActive, btDone;
    private TextView tvTgl, tvUser;

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    ArrayList<Task> list;
    GetDate waktu = new GetDate();
    FirebaseInit fb = new FirebaseInit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvTgl = findViewById(R.id.tvTgl);
        tvUser = findViewById(R.id.tvNama);
        btMenu = findViewById(R.id.FbtMenu);
        btAdd = findViewById(R.id.FbtAdd);
        btAddcourse = findViewById(R.id.FbtAddcourse);
        btActive = findViewById(R.id.IbtActivehome);
        btDone = findViewById(R.id.IbtDonehome);
        recyclerView = findViewById(R.id.rvHome);

        tvTgl.setText(waktu.getDateNow("E, dd MMMM yyyy"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
        list = new ArrayList<>();
        taskAdapter = new TaskAdapter(this,list);
        recyclerView.setAdapter(taskAdapter);

        fb.mDatabaseRef.child(fb.mAuth.getUid()).child("Task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Toast.makeText(Home.this, "Succes " + dataSnapshot.getValue(Task.class), Toast.LENGTH_SHORT).show();
                    Task task = dataSnapshot.getValue(Task.class);
                    list.add(task);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddReminder.class);
                startActivity(intent);
                finish();
            }
        });

        btAddcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddCourse.class);
                startActivity(intent);
                finish();
            }
        });
    }


}