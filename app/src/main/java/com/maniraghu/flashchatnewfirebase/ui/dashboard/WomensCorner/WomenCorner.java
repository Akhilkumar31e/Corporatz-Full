package com.maniraghu.flashchatnewfirebase.ui.dashboard.WomensCorner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WomenCorner extends Fragment {

    private WomenCornerViewModel mViewModel;
    private HomeViewModel homeViewModel;
    private WorkingWomenFeed mNewsFeed;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private List<WorkingWomenInfo> mNews;
    private ProgressBar mProgressCircle;
    public static WomenCorner newInstance() {
        return new WomenCorner();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.women_corner_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WomenCornerViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("womenscorner");
        mAuth=FirebaseAuth.getInstance();
        mNews=new ArrayList<>();
        mProgressCircle=getActivity().findViewById(R.id.progress_circle);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    WorkingWomenInfo newsInformation=postSnapshot.getValue(WorkingWomenInfo.class);

                    mNews.add(newsInformation);
                }
                Collections.reverse(mNews);
                mNewsFeed=new WorkingWomenFeed(getContext(),mNews);
                recyclerView.setAdapter(mNewsFeed);
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
