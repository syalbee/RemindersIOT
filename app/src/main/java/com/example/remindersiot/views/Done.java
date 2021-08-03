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

import com.example.remindersiot.R;
import com.example.remindersiot.adapter.DoneAdapter;
import com.example.remindersiot.adapter.TaskAdapter;
import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.model.DoneModel;
import com.example.remindersiot.model.TaskModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Done extends AppCompatActivity {

    private DoneAdapter adapter;
    private ArrayList<DoneModel> list;
    private ImageButton btnBack;

    RecyclerView recyclerView;
    FirebaseInit fb = new FirebaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        recyclerView = findViewById(R.id.rvDone);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new DoneAdapter(this, list);
        recyclerView.setAdapter(adapter);

        btnBack = findViewById(R.id.Ibtbackdone);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        fb.database.getReference().child("User").child(fb.mAuth.getUid()).child("Done")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            DoneModel model = dataSnapshot.getValue(DoneModel.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}