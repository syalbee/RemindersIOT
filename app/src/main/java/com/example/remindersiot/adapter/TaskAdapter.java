package com.example.remindersiot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindersiot.R;
import com.example.remindersiot.data.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<Task> list;

    public TaskAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        Task task = list.get(position);
        holder.course.setText(task.getCourse());
        holder.start.setText(task.getStart());
        holder.due.setText(task.getDue());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course, start, due;
        ImageView ivWarna;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            course = itemView.findViewById(R.id.tvNCourse);
            start = itemView.findViewById(R.id.tvNStart);
            due = itemView.findViewById(R.id.tvNDue);
//            ivWarna = itemView.findViewById(R.id.ivWarnaHome);
        }
    }
}
