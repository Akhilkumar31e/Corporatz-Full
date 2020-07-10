package com.maniraghu.flashchatnewfirebase.ui.dashboard.WellBeingCorner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.home.NewsInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WellBeing extends Fragment {

    private WellBeingViewModel mViewModel;
    private WellBeingViewer mWellBeingViewer;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private List<NewsInformation> mNews;
    private ProgressBar mProgressCircle;
    public static WellBeing newInstance() {
        return new WellBeing();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.well_being_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WellBeingViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Well Being Corner");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.well_being_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("wellbeing");
        mAuth=FirebaseAuth.getInstance();
        mNews=new ArrayList<>();
        mProgressCircle=getActivity().findViewById(R.id.well_being_progress_circle);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    NewsInformation newsInformation=postSnapshot.getValue(NewsInformation.class);

                    mNews.add(newsInformation);
                }
                Collections.reverse(mNews);
                mWellBeingViewer=new WellBeingViewer(getContext(),mNews);
                recyclerView.setAdapter(mWellBeingViewer);
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
