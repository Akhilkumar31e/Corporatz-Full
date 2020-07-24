package com.maniraghu.flashchatnewfirebase.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.home.SearchRecyclerView;
import com.maniraghu.flashchatnewfirebase.ui.home.SearchUserInfo;

import java.util.ArrayList;
import java.util.List;

public class FollowList extends AppCompatActivity {
    private String type;
    private  String userId;
    private RecyclerView mRecyclerView;
    private DatabaseReference followDatabase,userDatabase;
    private ProgressBar mProgressCircle;
    private List<SearchUserInfo> mList;
    private SearchRecyclerView followAdapter;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        type = getIntent().getExtras().getString("type");
        getSupportActionBar().setTitle(type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId=getIntent().getExtras().getString("userId");
        mRecyclerView=findViewById(R.id.follow_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle=findViewById(R.id.follow_progress_circle);
        followDatabase = FirebaseDatabase.getInstance().getReference().child("people").child(type).child(userId);
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mList=new ArrayList<>();
        Log.d("User",userId);
        followDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user: dataSnapshot.getChildren()){
                    final String followId= user.getKey();
                    String username= user.getValue(String.class);
                    SearchUserInfo userData = new SearchUserInfo(username,followId);
                    mList.add(userData);
                }
                Log.d("list",mList.toString());
                followAdapter = new SearchRecyclerView(getApplicationContext(),mList);
                mRecyclerView.setAdapter(followAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


    }
}
