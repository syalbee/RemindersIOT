package com.example.remindersiot.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.remindersiot.R;
import com.example.remindersiot.model.DoneModel;
import com.example.remindersiot.views.DetailDone;

import java.util.ArrayList;
import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    Context context;
    ArrayList<DoneModel> list;

    public DoneAdapter(Context context, ArrayList<DoneModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new TaskAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        DoneModel task = list.get(position);
        holder.course.setText(task.getCourse());
        holder.start.setText("Start: " + task.getStart());
        holder.due.setText("Due: " + task.getDue());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tugas = new ArrayList<String>();
                tugas.add(task.getCourse());
                tugas.add(task.getDue());
                tugas.add(task.getIdKey());
                tugas.add(task.getNote());
                tugas.add(task.getStart());
                tugas.add(task.getTitle());
                Intent intent = new Intent(context, DetailDone.class);
                intent.putStringArrayListExtra("tugas", (ArrayList<String>) tugas);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView course, start, due;
//        ImageView ivWarna;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            course = itemView.findViewById(R.id.tvNCourse);
            start = itemView.findViewById(R.id.tvNStart);
            due = itemView.findViewById(R.id.tvNDue);
//            ivWarna = itemView.findViewById(R.id.ivWarnaHome);
        }
    }
}
