package com.example.remindersiot.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.getset.Tasks;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btDone;
    private TextView tvcourse,tvdue,tvnote,tvstart,tvtitle;
    FirebaseInit fb = new FirebaseInit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvcourse = findViewById(R.id.tvCoursedone);
        tvdue = findViewById(R.id.tvDuedone);
        tvnote = findViewById(R.id.tvNotedone);
        tvstart = findViewById(R.id.tvStartdone);
        tvtitle = findViewById(R.id.tvTitledone);
        btDone = findViewById(R.id.btDonedetail);

        btnBack = findViewById(R.id.Ibtbackdetail);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail.this, Home.class);
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
        String idHapus = test.get(2);
        String idurut = test.get(6);

        tvcourse.setText(course);
        tvdue.setText("Due: "+due);
        tvnote.setText(note);
        tvstart.setText("Start: "+start);
        tvtitle.setText(title);

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetail(title,course,start,due, note, idHapus, idurut);
            }
        });
    }

    private void setDetail(String title, String course, String start, String due, String note, String idHapus, String idurut){

        String uid = fb.mAuth.getCurrentUser().getUid();
        String idKey = fb.mDatabaseRef.push().getKey();

        Tasks tks = new Tasks(title, course, start, due,note, idKey, idurut);

        fb.database.getReference("User").child(uid).child("Done").child(idKey).setValue(tks);
        fb.database.getReference("User").child(uid).child("Task").child(idHapus).removeValue();
        fb.database.getReference("User").child(uid).child("TaskDevice").child(idurut).removeValue();
        Toast.makeText(Detail.this, "Congrats your task is Done", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Detail.this, Home.class);
        startActivity(intent);
        finish();
    }
}