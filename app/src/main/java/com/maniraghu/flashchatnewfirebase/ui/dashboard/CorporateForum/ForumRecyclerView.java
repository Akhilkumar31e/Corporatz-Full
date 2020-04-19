package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;

import java.util.List;

public class ForumRecyclerView extends RecyclerView.Adapter<ForumRecyclerView.ForumViewHolder> {
    private Context mContext;
    private List<Query> mQueryList;

    public ForumRecyclerView(Context mContext, List<Query> mQueryList) {
        this.mContext = mContext;
        this.mQueryList = mQueryList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.query_items,parent,false);
        return new ForumRecyclerView.ForumViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        final Query queries=mQueryList.get(position);
        final String post_key=queries.getqQueryId();
        holder.username.setText(queries.getqUsername()+" posted a query");
        holder.time.setText(queries.getqTime());
        holder.desc.setText(queries.getqQuery());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singleQuery=new Intent(mContext,SingleQueryPage.class);
                singleQuery.putExtra("queryId",post_key);
                mContext.startActivity(singleQuery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQueryList.size();
    }

    public  class ForumViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView desc;
        public TextView username;
        public TextView time;
        public ForumViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            username=itemView.findViewById(R.id.query_username);
            desc = itemView.findViewById(R.id.query_desc);
            time=itemView.findViewById(R.id.query_post_time);
        }
    }
}
