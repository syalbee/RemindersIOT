package com.example.remindersiot.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.remindersiot.MainActivity;
import com.example.remindersiot.R;
import com.example.remindersiot.data.GetDate;
import com.example.remindersiot.database.FirebaseInit;
import com.example.remindersiot.getset.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.app.DatePickerDialog;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddReminder extends AppCompatActivity {

    private EditText etTitle, etStart, etDue, etNote;
    private Spinner etCourse;
    private SimpleDateFormat dateFormatter;
    private Button btCreate;
    private ImageButton btnBack;
    FirebaseInit fb = new FirebaseInit();
    GetDate gd = new GetDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        etTitle = findViewById(R.id.etTasktitle);
        etCourse = findViewById(R.id.etCourseadd);
        etStart = findViewById(R.id.etStartadd);
        etDue = findViewById(R.id.etDueadd);
        etNote = findViewById(R.id.etNoteadd);
        btCreate = findViewById(R.id.btCreateadd);
        btnBack = findViewById(R.id.Ibtbackadd);

        setSpinner();

//        etStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                showDateDialog("start");
//            }
//        });
//        etDue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                showDateDialog("due");
//            }
//        });
        etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog("start");
            }
        });

        etDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog("due");
            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pushTask();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddReminder.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void showDateDialog(String jenis){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if(jenis.equals("start")){
                    etStart.setText(dateFormatter.format(newDate.getTime()));
                } else{
                    etDue.setText(dateFormatter.format(newDate.getTime()));
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void setSpinner(){
        fb.database.getReference("User").child(fb.mAuth.getUid()).child("Course").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> ListCourse = new ArrayList<String>();
                for(DataSnapshot coursesnapshot: snapshot.getChildren()){
                    ListCourse.add(coursesnapshot.getValue().toString());
                }
                ArrayAdapter<String> ListAdapter = new ArrayAdapter<String>(AddReminder.this, android.R.layout.simple_spinner_item, ListCourse);
                ListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                etCourse.setAdapter(ListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pushTask(){
        String uid = fb.mAuth.getCurrentUser().getUid();
        String title = etTitle.getText().toString();
        String course = etCourse.getSelectedItem().toString();
        String start = etStart.getText().toString();
        String due = etDue.getText().toString();
        String note = etNote.getText().toString();
        String idKey = fb.mDatabaseRef.push().getKey();

        Tasks tks = new Tasks(title, course, start, due,note, idKey);
        etTitle.setText("");
        etStart.setText("");
        etDue.setText("");
        etNote.setText("");
        fb.database.getReference("User").child(uid).child("Task").child(idKey).setValue(tks);
        Toast.makeText(AddReminder.this, "Add Task Succes", Toast.LENGTH_LONG).show();
    }
}