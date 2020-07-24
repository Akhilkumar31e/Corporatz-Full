package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel mViewModel;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase,ref;
    private List<RideInformation> mRide;
    private ProgressBar progressCircle;
    private History history;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private HashSet<String> hashRide;
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        // TODO: Use the ViewModel
            recyclerView=(RecyclerView)getActivity().findViewById(R.id.history_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            mDatabase= FirebaseDatabase.getInstance().getReference("history");
            mRide=new ArrayList<>();
            hashRide=new HashSet<>();
            mAuth=FirebaseAuth.getInstance();
            mUser=mAuth.getCurrentUser();
            progressCircle=(ProgressBar)getActivity().findViewById(R.id.history_progress_circle);
            ref=mDatabase.child(mUser.getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snap:dataSnapshot.getChildren()){
                        RideInformation ri=snap.getValue(RideInformation.class);
                        if(ri.getUserId().equals(mUser.getUid().toString()))
                          if(!hashRide.contains(ri.getRideId()))  mRide.add(ri);
                          hashRide.add(ri.getRideId());
                    }
                    Collections.reverse(mRide);
                    history=new History(getContext(),mRide);
                    recyclerView.setAdapter(history);
                    progressCircle.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

}
