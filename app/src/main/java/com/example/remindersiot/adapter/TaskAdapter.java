package com.example.remindersiot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindersiot.R;
import com.example.remindersiot.model.TaskModel;
import com.example.remindersiot.views.Detail;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    Context context;
    ArrayList<TaskModel> list;

    public TaskAdapter(Context context, ArrayList<TaskModel> list) {
        this.context = context;
        this.list = list;
    }

     @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TaskModel task = list.get(position);

        String[] ke =  task.getDue().split("-");
        String hari = ke[0];
        String bulan = ke[1];
        String tahun = ke[2];

        int perbedaan = Period
                .between(LocalDate.now(), LocalDate.of(Integer.parseInt(tahun)
                        ,Integer.parseInt(bulan),Integer.parseInt(hari)))
                .getDays();
        Log.d("cek", String.valueOf(perbedaan));

        if(perbedaan > 7) {
            //Warna biru
            Log.d("biru", String.valueOf(perbedaan));
            holder.ivWarna.setBackgroundResource(R.color.blue);
        } else if(perbedaan <= 7 && perbedaan > 3){
            //Warna kuning
            Log.d("kuning", String.valueOf(perbedaan));
            holder.ivWarna.setBackgroundResource(R.color.yellow);
        } else if(perbedaan <= 3){
            //Warna Merah
            Log.d("merah", String.valueOf(perbedaan));
            holder.ivWarna.setBackgroundResource(R.color.red);
        }

        holder.title.setText(task.getTitle());
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
                tugas.add(task.getIdUrut());
                Intent intent = new Intent(context, Detail.class);
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
        TextView course, start, due, title;
        ImageView ivWarna;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            course = itemView.findViewById(R.id.tvNCourse);
            start = itemView.findViewById(R.id.tvNStart);
            due = itemView.findViewById(R.id.tvNDue);
            title = itemView.findViewById(R.id.tvNTitle);
            ivWarna = itemView.findViewById(R.id.viewWarna);

        }
    }
}
