package com.maniraghu.flashchatnewfirebase.ui.dashboard.WomensCorner;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.squareup.picasso.Picasso;

public class WomenSinglePost extends AppCompatActivity {
    private ImageView img;
    private TextView singleTitle,singleDesc;
    String title;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("More");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabase = FirebaseDatabase.getInstance().getReference("womenscorner");
        mAuth=FirebaseAuth.getInstance();
        img = (ImageView)findViewById(R.id.imageView);

        singleTitle = (TextView)findViewById(R.id.singleTitle);
        singleDesc = (TextView)findViewById(R.id.singleDesc);
        title=getIntent().getExtras().getString("post");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    WorkingWomenInfo newsInformation=d.getValue(WorkingWomenInfo.class);
                    if(newsInformation.getPostTitle().equals(title)){
                        singleTitle.setText(newsInformation.getPostTitle());
                        singleDesc.setText(newsInformation.getPostDesc());
                        Picasso.with(WomenSinglePost.this).load(newsInformation.getPostUrl())
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
