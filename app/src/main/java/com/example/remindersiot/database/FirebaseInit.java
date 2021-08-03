package com.example.remindersiot.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseInit {
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    public FirebaseUser user;
    public FirebaseDatabase database;

    public DatabaseReference users;
    public DatabaseReference mDatabaseRef;
    public DatabaseReference getKey;

    public FirebaseInit(){
        mAuth       = FirebaseAuth.getInstance();
        user        = mAuth.getCurrentUser();
        database    = FirebaseDatabase.getInstance();

        users       = database.getReference("Users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("User");
    }
}
