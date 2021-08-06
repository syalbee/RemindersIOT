package com.example.remindersiot.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.remindersiot.R;
import com.example.remindersiot.database.FirebaseInit;


public class Settings extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btSet, btLogout;
    private EditText etMac;
    FirebaseInit fb = new FirebaseInit();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.Ibtbacksettings);
        btLogout = findViewById(R.id.btLogout);
        etMac = findViewById(R.id.etMacAddress);
        btSet = findViewById(R.id.btsetMac);

        btSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String macaddress = etMac.getText().toString();
                if(macaddress.equals("")){
                    Toast.makeText(Settings.this, "This input not null", Toast.LENGTH_LONG).show();

                } else {

                    fb.database.getReference("User").child(fb.mAuth.getCurrentUser().getUid()).child("Device").child("macaddress").setValue(macaddress);
                    fb.database.getReference("User").child(fb.mAuth.getCurrentUser().getUid()).child("Device").child("status").setValue("connected");
                    fb.database.getReference("Device").child(macaddress).child("uid").setValue(fb.mAuth.getUid());
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb.mAuth.signOut();
                finish();
            }
        });
    }
}