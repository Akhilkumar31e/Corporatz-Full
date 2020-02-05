package com.maniraghu.flashchatnewfirebase.ui.dashboard.WomensCorner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.home.SinglePostActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WorkingWomenFeed extends RecyclerView.Adapter<WorkingWomenFeed.WomenViewHolder> {
    private Context mContext;
    private List<WorkingWomenInfo> mInfo;

    public WorkingWomenFeed(Context mContext, List<WorkingWomenInfo> mInfo) {
        this.mContext = mContext;
        this.mInfo = mInfo;
    }

    @NonNull
    @Override
    public WomenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.womens_feed_items,parent,false);
        return new WorkingWomenFeed.WomenViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull WomenViewHolder holder, int position) {
        final WorkingWomenInfo current=mInfo.get(position);
        holder.desc.setText(current.getPostDesc());
        holder.title.setText(current.getPostTitle());
        Picasso.with(mContext)
                .load(current.getPostUrl())
                .placeholder(R.drawable.grad)
                .fit()
                .centerCrop()
                .into(holder.imgView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, SinglePostActivity.class);
                next.putExtra("post",current.getPostTitle());
                mContext.startActivity(next);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfo.size();
    }

    public  class WomenViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView title;
        public TextView desc;
        public ImageView imgView;
        public WomenViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            title = itemView.findViewById(R.id.post_title);
            desc = itemView.findViewById(R.id.post_desc);
            imgView = itemView.findViewById(R.id.post_image);
        }
    }
}
