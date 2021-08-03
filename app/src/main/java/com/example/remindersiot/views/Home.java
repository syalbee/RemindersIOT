package com.example.remindersiot.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.remindersiot.R;
import com.example.remindersiot.adapter.TaskAdapter;
import com.example.remindersiot.data.GetDate;
import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.model.TaskModel;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private FloatingActionButton btMenu, btAdd, btAddcourse;
    private TextView tvTgl, tvUser,countactive, countdone;
    private Button btnActive, btnDone;
    private ImageView ivStatus;
    private TaskAdapter adapter;
    private ArrayList<TaskModel> list;


    RecyclerView recyclerView;
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
        btnActive = findViewById(R.id.btActivehome);
        btnDone = findViewById(R.id.btDonehome);
        countactive = findViewById(R.id.tvCountActive);
        countdone = findViewById(R.id.tvCountDone);
        ivStatus = findViewById(R.id.ivDevicestatus);

        recyclerView = findViewById(R.id.rvHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new TaskAdapter(this, list);
        recyclerView.setAdapter(adapter);

        tvTgl.setText(waktu.getDateNow("E, dd MMMM yyyy"));
        fb.database.getReference().child("User").child(fb.mAuth.getUid())
                .child("Akun").child("nama")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String namaHead = (String) snapshot.getValue();
                        String[] namake =  namaHead.split(" ");
                        tvUser.setText("Hi, " + namake[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Active.class);
                startActivity(intent);
                finish();
            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Done.class);
                startActivity(intent);
                finish();
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

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Settings.class);
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

        fb.database.getReference().child("User").child(fb.mAuth.getUid()).child("Task")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                      long count = snapshot.getChildrenCount();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            TaskModel model = dataSnapshot.getValue(TaskModel.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                        countactive.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        fb.database.getReference().child("User").child(fb.mAuth.getUid()).child("Done")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        long count = snapshot.getChildrenCount();
                        countdone.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        fb.database.getReference().child("User").child(fb.mAuth.getUid()).child("Device").child("status")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                       String status = (String) snapshot.getValue();
                       Log.d("status", status);
                       if(status.equals("connected")){
                            ivStatus.setImageResource(R.drawable.ic_sync_64);
                       } else if(status.equals("disconnected")){
                           ivStatus.setImageResource(R.drawable.ic_sync_disabled_74);
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


}