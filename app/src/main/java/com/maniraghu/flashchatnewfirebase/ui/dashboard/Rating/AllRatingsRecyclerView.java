package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.ProfilePageActivity;
import com.maniraghu.flashchatnewfirebase.ui.profile.UserInformation;

import java.util.List;

public class AllRatingsRecyclerView extends RecyclerView.Adapter<AllRatingsRecyclerView.AllRatingsViewHolder> {
    private Context mContext;
    private List<Rating> mRatings;
    private DatabaseReference mDatabase;

    public AllRatingsRecyclerView(Context mContext, List<Rating> mRatings) {
        this.mContext = mContext;
        this.mRatings = mRatings;
    }

    @NonNull
    @Override
    public AllRatingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.rating_card_items,parent,false);
        return new AllRatingsRecyclerView.AllRatingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllRatingsViewHolder holder, int position) {
        final Rating ratings=mRatings.get(position);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        final String uId=ratings.getUserId();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation user=dataSnapshot.child(uId).getValue(UserInformation.class);
                holder.username.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, ProfilePageActivity.class);
                next.putExtra("userid",ratings.getUserId());
                mContext.startActivity(next);
            }
        });
        holder.companyName.setText(ratings.getCompanyName());
        holder.review.setText(ratings.getCompanyReview());
        Float rValue=Float.parseFloat(ratings.companyRating);
        holder.rating.setRating(rValue);


    }

    @Override
    public int getItemCount() {
        return mRatings.size();
    }

    public class AllRatingsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView username,companyName,review;
        public RatingBar rating;

        public AllRatingsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            username=itemView.findViewById(R.id.rating_card_username);
            companyName=itemView.findViewById(R.id.rating_card_company);
            review=itemView.findViewById(R.id.rating_card_review);
            rating=itemView.findViewById(R.id.rating_card_stars);
        }
    }
}
