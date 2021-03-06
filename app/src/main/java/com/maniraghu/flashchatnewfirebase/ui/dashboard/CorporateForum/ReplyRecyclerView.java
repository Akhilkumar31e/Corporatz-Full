package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.ProfilePageActivity;

import java.util.List;

public class ReplyRecyclerView extends RecyclerView.Adapter<ReplyRecyclerView.ReplyViewHolder> {
    private Context mContext;
    private List<Query> mReplyList;
    private DatabaseReference replyDatabase;
    private FirebaseUser user;

    public ReplyRecyclerView(Context mContext, List<Query> mReplyList) {
        this.mContext = mContext;
        this.mReplyList = mReplyList;
        user= FirebaseAuth.getInstance().getCurrentUser();
        replyDatabase= FirebaseDatabase.getInstance().getReference().child("replies");
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
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, ProfilePageActivity.class);
                next.putExtra("userid",queries.getqId());
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(next);
            }
        });
        String uId=user.getUid();
        if(uId.equals(queries.getqId())){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replyDatabase.child(SingleQueryPage.query_id).child(post_key).removeValue();
                }
            });
        }
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
        public ImageButton delete;
        public ReplyViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            username=itemView.findViewById(R.id.reply_username);
            desc = itemView.findViewById(R.id.reply_desc);
            time=itemView.findViewById(R.id.reply_post_time);
            delete=itemView.findViewById(R.id.deleteReplyButton);
        }
    }
}
