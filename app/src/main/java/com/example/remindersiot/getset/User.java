package com.example.remindersiot.getset;

public class User {

    public String nama, email, password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String nama, String email, String password) {
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

}
