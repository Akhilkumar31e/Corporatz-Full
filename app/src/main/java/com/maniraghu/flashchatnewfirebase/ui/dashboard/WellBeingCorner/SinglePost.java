package com.maniraghu.flashchatnewfirebase.ui.dashboard.WellBeingCorner;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.home.NewsInformation;
import com.squareup.picasso.Picasso;

public class SinglePost extends AppCompatActivity {
    private ImageView img;
    private TextView singleTitle,singleDesc;
    String title;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        mDatabase = FirebaseDatabase.getInstance().getReference("wellbeing");
        mAuth=FirebaseAuth.getInstance();
        img = (ImageView)findViewById(R.id.imageView);

        singleTitle = (TextView)findViewById(R.id.singleTitle);
        singleDesc = (TextView)findViewById(R.id.singleDesc);
        title=getIntent().getExtras().getString("post");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    NewsInformation newsInformation=d.getValue(NewsInformation.class);
                    if(newsInformation.getTitle().equals(title)){
                        singleTitle.setText(newsInformation.getTitle());
                        singleDesc.setText(newsInformation.getDesc());
                        Picasso.with(SinglePost.this).load(newsInformation.getImgurl())
                                .placeholder(R.drawable.grad)
                                .into(img);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
