package com.maniraghu.flashchatnewfirebase.ui.dashboard.WellBeingCorner;

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
import com.maniraghu.flashchatnewfirebase.ui.home.NewsInformation;
import com.maniraghu.flashchatnewfirebase.ui.home.SinglePostActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WellBeingViewer extends RecyclerView.Adapter<WellBeingViewer.WellBeingViewHolder> {

    private Context mContext;
    private List<NewsInformation> mNews;

    public WellBeingViewer(Context context,List<NewsInformation> news){
        mContext=context;
        mNews=news;
    }
    @NonNull
    @Override
    public WellBeingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.well_being_items,parent,false);
        return new WellBeingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WellBeingViewHolder holder, int position) {
        final NewsInformation current=mNews.get(position);
        holder.desc.setText(current.getDesc());
        holder.title.setText(current.getTitle());
        Picasso.with(mContext)
                .load(current.getImgurl())
                .placeholder(R.drawable.grad)
                .fit()
                .centerCrop()
                .into(holder.imgView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, SinglePost.class);
                next.putExtra("post",current.getTitle());
                mContext.startActivity(next);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public  class WellBeingViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView title;
        public TextView desc;
        public ImageView imgView;
        public WellBeingViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            title = itemView.findViewById(R.id.well_being_title);
            desc = itemView.findViewById(R.id.well_being_desc);
            imgView = itemView.findViewById(R.id.well_being_image);
        }
    }
}