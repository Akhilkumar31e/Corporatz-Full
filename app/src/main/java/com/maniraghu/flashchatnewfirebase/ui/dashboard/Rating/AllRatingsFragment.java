package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllRatingsFragment extends Fragment {

    private AllRatingsViewModel mViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressCircle;
    private DatabaseReference mDatabase;
    private List<Rating> ratingList;
    AllRatingsRecyclerView allRatingsRecyclerView;

    public static AllRatingsFragment newInstance() {
        return new AllRatingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_ratings_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AllRatingsViewModel.class);
        // TODO: Use the ViewModel
        recyclerView=getActivity().findViewById(R.id.all_ratings_recycler_view);
        progressCircle=getActivity().findViewById(R.id.all_ratings_progress_circle);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("ratingInfo");
        ratingList=new ArrayList<Rating>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Rating rating=ds.getValue(Rating.class);
                    ratingList.add(rating);
                }
                Collections.reverse(ratingList);
                allRatingsRecyclerView=new AllRatingsRecyclerView(getContext(),ratingList);
                recyclerView.setAdapter(allRatingsRecyclerView);
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

}
