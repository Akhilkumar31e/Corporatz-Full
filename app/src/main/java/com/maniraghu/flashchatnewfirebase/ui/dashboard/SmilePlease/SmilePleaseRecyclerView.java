package com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.ProfilePageActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SmilePleaseRecyclerView extends RecyclerView.Adapter<SmilePleaseRecyclerView.SmilePleaseViewHolder> {
    private Context mContext;
    private List<Upload> mPost;
    private DatabaseReference mDatabase,notiDatabase;
    private FirebaseAuth mAuth;
    private boolean mProcessLike=false;
    private boolean mProcessDisike=false;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public SmilePleaseRecyclerView(Context mContext, List<Upload> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public SmilePleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.smile_please_post_items,parent,false);
        return new SmilePleaseRecyclerView.SmilePleaseViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull final SmilePleaseViewHolder holder, int position) {
        final Upload uploads=mPost.get(position);
        final String post_key=uploads.getmPostId();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Reactions");
        notiDatabase = FirebaseDatabase.getInstance().getReference().child("notifications").child(uploads.getmId());
        mAuth=FirebaseAuth.getInstance();
        notiDatabase = FirebaseDatabase.getInstance().getReference().child("notifications").child(uploads.getmId());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.noOfLikes.setText(dataSnapshot.child("Likes").child(post_key).getChildrenCount() +" likes");
                holder.noOfDislikes.setText(dataSnapshot.child("Dislikes").child(post_key).getChildrenCount() +" dislikes");
               // likes=dataSnapshot.child(post_key).getChildrenCount();
                if(dataSnapshot.child("Likes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                    holder.like.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                }
                else holder.like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                if(dataSnapshot.child("Dislikes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                    holder.dislike.setImageResource(R.drawable.ic_thumb_down_blue_24dp);
                }
                else holder.dislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.username.setText(uploads.getmUsername());
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next=new Intent(mContext, ProfilePageActivity.class);
                next.putExtra("userid",uploads.getmId());
                mContext.startActivity(next);
            }
        });
        holder.time.setText(uploads.getmTime());
        holder.desc.setText(uploads.getmDesc());
        String taggedUser=uploads.getmTaggedUserId();
        if(taggedUser!=null&&taggedUser!=""){
            holder.userTag.setText("Tagged users : @"+taggedUser);
        }
        Picasso.with(mContext)
                .load(uploads.getmImageUrl())
                .placeholder(R.drawable.grad)
                .fit()
                .centerCrop()
                .into(holder.imgView);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProcessLike=true;
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (mProcessLike) {
                                if (dataSnapshot.child("Likes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                    mDatabase.child("Likes").child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                    holder.like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                                    mProcessLike=false;
                                } else {
                                    if(dataSnapshot.child("Dislikes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                        mDatabase.child("Dislikes").child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        holder.dislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                                    }
                                    mDatabase.child("Likes").child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("Like");
                                    String push_key = notiDatabase.push().getKey();
                                    String notiMsg="You reaceived a like for your post";
                                    calendar= Calendar.getInstance();
                                    dateFormat=new SimpleDateFormat("MMMM dd,yyyy HH:mm:ss", Locale.US);
                                    /*String time=dateFormat.format(calendar.getTime());
                                    final Notification newNoti= new Notification(notiMsg,time);
                                    notiDatabase.child(push_key).setValue(newNoti);
                                    Log.d("user like",uploads.getmId());*/
                                    holder.like.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                                    mProcessLike = false;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            }
        });
        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProcessDisike=true;
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(mProcessDisike){
                            if(dataSnapshot.child("Dislikes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                                mDatabase.child("Dislikes").child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                holder.dislike.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                                mProcessDisike=false;
                            }
                            else{
                                if (dataSnapshot.child("Likes").child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                    mDatabase.child("Likes").child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                    holder.like.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                                }
                                mDatabase.child("Dislikes").child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("Dislike");
                                holder.dislike.setImageResource(R.drawable.ic_thumb_down_blue_24dp);

                                mProcessDisike=false;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Just a moment!",Toast.LENGTH_LONG).show();
                String shareMessage = "Check out this photo by "+uploads.getmUsername()+" from CORPORATZ,The employees' app";
                shareItem(uploads.getmImageUrl(),shareMessage);
            }
        });
    }

    public void shareItem(final String url, final String shareMessage) {
        Picasso.with(mContext).load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_TEXT,shareMessage);
                i.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap));
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(Intent.createChooser(i, "Share Image"));
            }
            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public  class SmilePleaseViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TextView desc;
        public TextView username;
        public ImageView imgView;
        public TextView time;
        public TextView noOfLikes;
        public TextView noOfDislikes;
        public ImageButton like;
        public ImageButton dislike;
        public TextView userTag;
        public LinearLayout share;
        public SmilePleaseViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            username=itemView.findViewById(R.id.smile_username);
            desc = itemView.findViewById(R.id.smile_description);
            imgView = itemView.findViewById(R.id.smile_image);
            time=itemView.findViewById(R.id.smile_post_time);
            like=itemView.findViewById(R.id.smile_like_button);
            dislike=itemView.findViewById(R.id.smile_dislike_button);
            noOfLikes=itemView.findViewById(R.id.smile_likes);
            noOfDislikes=itemView.findViewById(R.id.smile_dislikes);
            userTag= itemView.findViewById(R.id.smile_tagged_username);
            share=itemView.findViewById(R.id.smile_share_layout);
        }
    }
}
