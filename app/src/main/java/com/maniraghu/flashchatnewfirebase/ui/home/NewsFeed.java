package com.maniraghu.flashchatnewfirebase.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsFeed extends RecyclerView.Adapter<NewsFeed.NewsFeedViewHolder> {

    private Context mContext;
    private List<NewsInformation> mNews;

    public NewsFeed(Context context,List<NewsInformation> news){
        mContext=context;
        mNews=news;
    }
    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.newsfeed_items,parent,false);
        return new NewsFeedViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder holder, int position) {
        NewsInformation current=mNews.get(position);
        holder.desc.setText(current.getDesc());
        holder.title.setText(current.getTitle());
        Picasso.with(mContext)
                .load(current.getImgurl())
                .placeholder(R.drawable.grad)
                .fit()
                .centerCrop()
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public  class NewsFeedViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView desc;
        public ImageView imgView;
        public NewsFeedViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.img_title);
            desc = itemView.findViewById(R.id.img_desc);
            imgView = itemView.findViewById(R.id.news_image);
        }

    }
}
