package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SingleQueryPage extends AppCompatActivity {
    private List<Query> queryList;
    private HashSet<String> uniq;
    private ReplyRecyclerView Reply;
    private RecyclerView recyclerView;
    private ProgressBar progressCircle;
    private TextView username;
    private TextView time;
    private TextView desc;
    private DatabaseReference mReference;
    private String query_id;
    private Button post;
    private EditText replyText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uId,currUser;
    private DatabaseReference mDatabaseRef,mUserDatabase,mReplyDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_query_page);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Query");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.reply_recyclerview);
        progressCircle=findViewById(R.id.reply_progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager((getApplicationContext())));
        recyclerView.setHasFixedSize(true);
        post=findViewById(R.id.reply_button);
        replyText=findViewById(R.id.reply_text);
        username=findViewById(R.id.single_query_username);
        time=findViewById(R.id.single_query_post_time);
        desc=findViewById(R.id.single_query_desc);
        query_id=getIntent().getStringExtra("queryId");
        String path="forum/"+query_id;
        mReference= FirebaseDatabase.getInstance().getReference(path);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query q=dataSnapshot.getValue(Query.class);
                username.setText(q.getqUsername());
                time.setText(q.getqTime());
                desc.setText(q.getqQuery());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //To store reply in database
        String replyPath="replies";
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(replyPath).child(query_id);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        uId=user.getUid();
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uId);
        calendar=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //time=dateFormat.format(calendar.getTime());
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currUser=dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uploadId = mDatabaseRef.push().getKey();
                Query query=new Query(replyText.getText().toString(),uploadId,currUser,dateFormat.format(calendar.getTime()),uId);
                mDatabaseRef.child(uploadId).setValue(query);
                replyText.setText("");
                Toast.makeText(getApplicationContext(),"Thanks for replying",Toast.LENGTH_LONG).show();
            }
        });

        //To display replies
        queryList=new ArrayList<>();
        uniq=new HashSet<>();
        String pathForReplies="replies/"+query_id;
        mReplyDatabase=FirebaseDatabase.getInstance().getReference(pathForReplies);
        mReplyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Query q=ds.getValue(Query.class);
                    if(!uniq.contains(q.getqQueryId())){
                        queryList.add(q);
                        uniq.add(q.getqQueryId());
                    }
                }
                Collections.reverse(queryList);
                Reply=new ReplyRecyclerView(getApplicationContext(),queryList);
                recyclerView.setAdapter(Reply);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }
}
