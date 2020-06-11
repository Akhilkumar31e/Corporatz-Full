package com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SmilePleaseViewer extends BaseFragment {

    private SmilePleaseViewerViewModel mViewModel;
    private Button post;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase,mReactionDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private List<Upload> mPost;
    private FirebaseAuth getmAuth;
    private FirebaseUser user;
    private SmilePleaseRecyclerView smilePleaseViewer;
    private ProgressBar mProgressCircle;
    private HashSet<Upload> hashSet;
    private String post_key;
    public static SmilePleaseViewer newInstance() {
        return new SmilePleaseViewer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smile_please_viewer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SmilePleaseViewerViewModel.class);
        // TODO: Use the ViewModel
        post=getActivity().findViewById(R.id.post_picture_button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActivity!=null) mActivity.navigateToFragment(new Smile(),null);
            }
        });
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.smile_recyclerview);
        mProgressCircle=getActivity().findViewById(R.id.smile_progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);

        mDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        mReactionDatabase = FirebaseDatabase.getInstance().getReference("Reactions");
        mAuth=FirebaseAuth.getInstance();
        mPost=new ArrayList<>();
        hashSet=new HashSet<>();
        getmAuth=FirebaseAuth.getInstance();
        user=getmAuth.getCurrentUser();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    final Upload upload=snapshot.getValue(Upload.class);
                    //if(!upload.getmId().equals(user.getUid().toString()))
                    post_key=upload.getmPostId();
                    mReactionDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            upload.setmLike(String.valueOf(dataSnapshot.child("Likes").child(post_key).getChildrenCount()));
                            upload.setmDislike(String.valueOf(dataSnapshot.child("Dislikes").child(post_key).getChildrenCount()));
                            Log.d("like",upload.getmLike());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(!hashSet.contains(upload)) {
                        mPost.add(upload);
                        hashSet.add(upload);
                    }

                }
                Collections.reverse(mPost);
                smilePleaseViewer=new SmilePleaseRecyclerView(getContext(),mPost);
                recyclerView.setAdapter(smilePleaseViewer);
               // Toast.makeText(getContext(),mPost.get(0).getmImageUrl(),Toast.LENGTH_LONG).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

}
