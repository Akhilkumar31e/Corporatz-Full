package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;

import java.util.List;

public class ReplyRecyclerView extends RecyclerView.Adapter<ReplyRecyclerView.ReplyViewHolder> {
    private Context mContext;
    private List<Query> mReplyList;

    public ReplyRecyclerView(Context mContext, List<Query> mReplyList) {
        this.mContext = mContext;
        this.mReplyList = mReplyList;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.reply_items,parent,false);
        return new ReplyRecyclerView.ReplyViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        final Query queries=mReplyList.get(position);
        final String post_key=queries.getqQueryId();
        holder.username.setText(queries.getqUsername()+" replied");
        holder.time.setText(queries.getqTime());
        holder.desc.setText(queries.getqQuery());
    }

    @Override
    public int getItemCount() {
        return mReplyList.size();
    }

    public  class ReplyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView desc;
        public TextView username;
        public TextView time;
        public ReplyViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            username=itemView.findViewById(R.id.reply_username);
            desc = itemView.findViewById(R.id.reply_desc);
            time=itemView.findViewById(R.id.reply_post_time);
        }
    }
}
