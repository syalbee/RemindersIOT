package com.example.remindersiot.getset;

public class Tasks {
    public String title;
    public String course;
    public String start;
    public String due;
    public String note;
    public String idKey;
    public String idUrut;

    public Tasks(){

    }


    public Tasks(String title, String course, String start, String due, String note, String idKey, String idUrut) {
        this.title = title;
        this.course = course;
        this.start = start;
        this.due = due;
        this.note = note;
        this.idKey = idKey;
        this.idUrut = idUrut;
    }
}
