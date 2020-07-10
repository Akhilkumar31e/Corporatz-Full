package com.maniraghu.flashchatnewfirebase.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final Context context;
    private List<Notification> dataset;

    public MyAdapter(Context context, List<Notification> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification data = dataset.get(position);
        holder.title.setText(data.getTitle());


        holder.subtitle.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, subtitle;

        public MyViewHolder(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.cell_title);
            subtitle = view.findViewById(R.id.cell_subtitle);
        }
    }
}
