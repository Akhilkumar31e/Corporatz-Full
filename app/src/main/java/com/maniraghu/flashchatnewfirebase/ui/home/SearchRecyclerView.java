package com.maniraghu.flashchatnewfirebase.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.ProfilePageActivity;

import java.util.List;

public class SearchRecyclerView extends RecyclerView.Adapter<SearchRecyclerView.UsersViewHolder> {

    private Context mContext;
    private List<SearchUserInfo> mList;

    public SearchRecyclerView(Context mContext, List<SearchUserInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.search_list_items,parent,false);
        return new SearchRecyclerView.UsersViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, int position) {
        final SearchUserInfo info=mList.get(position);
        holder.username.setText(info.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, ProfilePageActivity.class);
                next.putExtra("userid",info.getUserId());
                mContext.startActivity(next);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public   class UsersViewHolder extends  RecyclerView.ViewHolder {
        View mView;
        TextView username;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            username=itemView.findViewById(R.id.search_item_username);
        }
    }
}
