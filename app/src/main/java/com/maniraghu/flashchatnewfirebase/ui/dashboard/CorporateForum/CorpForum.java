package com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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

public class CorpForum extends Fragment {

    private CorpForumViewModel mViewModel;
    private List<Query> queryList;
    private HashSet<String> uniq;
    private ForumRecyclerView Forum;
    private RecyclerView recyclerView;
    private ProgressBar progressCircle;
    private Button post;
    private EditText queryText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String uId,username,time;
    private DatabaseReference mDatabaseRef,mUserDatabase;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    public static CorpForum newInstance() {
        return new CorpForum();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.corp_forum_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CorpForumViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Corporate Forum");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.query_recyclerview);
        progressCircle=getActivity().findViewById(R.id.query_progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        post=getActivity().findViewById(R.id.query_post_button);
        queryText=getActivity().findViewById(R.id.queryText);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("forum");
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
                username=dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uploadId = mDatabaseRef.push().getKey();
                Query query=new Query(queryText.getText().toString(),uploadId,username,dateFormat.format(calendar.getTime()),uId);
                mDatabaseRef.child(uploadId).setValue(query);
                queryText.setText("");
            }
        });
        queryList=new ArrayList<>();
        uniq=new HashSet<>();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
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
                Forum=new ForumRecyclerView(getContext(),queryList);
                recyclerView.setAdapter(Forum);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

}
