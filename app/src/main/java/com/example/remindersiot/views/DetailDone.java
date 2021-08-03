package com.example.remindersiot.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;

import java.util.ArrayList;

public class DetailDone extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvcourse,tvdue,tvnote,tvstart,tvtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_done);

        tvcourse = findViewById(R.id.tvCoursedonedetail);
        tvdue = findViewById(R.id.tvDuedonedetail);
        tvnote = findViewById(R.id.tvNotedonedetail);
        tvstart = findViewById(R.id.tvStartdonedetail);
        tvtitle = findViewById(R.id.tvTitledonedetail);

        btnBack = findViewById(R.id.Ibtbackdone);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailDone.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        ArrayList<String> test = intent.getStringArrayListExtra("tugas");

        String title = test.get(5);
        String course = test.get(0);
        String start = test.get(4);
        String due = test.get(1);
        String note = test.get(3);

        tvcourse.setText(course);
        tvdue.setText("Due: "+ due);
        tvnote.setText(note);
        tvstart.setText("Start: "+ start);
        tvtitle.setText(title);
    }
}