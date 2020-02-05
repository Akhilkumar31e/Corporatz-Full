package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.List;

public class RequestRideFragment extends Fragment {

    private RequestRideViewModel mViewModel;
    private RideRequest rideRequest;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private List<RideInformation> mRide;
    private ProgressBar progressCircle;
    private AutoCompleteTextView source;
    private AutoCompleteTextView destination;
    private TextView noResult;
    private Button search;
    public static RequestRideFragment newInstance() {
        return new RequestRideFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.request_ride_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RequestRideViewModel.class);
        // TODO: Use the ViewModel
        source=getActivity().findViewById(R.id.requestSource);
        destination=getActivity().findViewById(R.id.requestDestination);
        search=getActivity().findViewById(R.id.request_ride_button);
        noResult=getActivity().findViewById(R.id.noResultMessage);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                recyclerView=(RecyclerView)getActivity().findViewById(R.id.request_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                mDatabase= FirebaseDatabase.getInstance().getReference("poolInfo");
                mRide=new ArrayList<>();

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot rideSnapshot:dataSnapshot.getChildren()){
                            RideInformation rideInformation=rideSnapshot.getValue(RideInformation.class);

                            if(rideInformation.getSource().equals(source.getText().toString())&&rideInformation.getDestination().equals(destination.getText().toString()))
                            mRide.add(rideInformation);
                        }
                        if(mRide.size()==0){
                            noResult.setText("No rides available at the moment at "+source.getText().toString());
                        }
                        else {
                            noResult.setText("");
                            rideRequest = new RideRequest(getContext(), mRide);
                            recyclerView.setAdapter(rideRequest);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

}
