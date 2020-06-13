package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;


public class ReviewFragment extends Fragment {

    private ReviewViewModel mViewModel;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText review;
    Button submitReview;

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.review_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference("ratingInfo");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        review=getActivity().findViewById(R.id.review_text);
        mViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        submitReview=getActivity().findViewById(R.id.review_submit_button);

        submitReview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String reviewMessage=review.getText().toString();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChild(mUser.getUid())){
                            Toast.makeText(getActivity(),"Please provide rating before you write review",Toast.LENGTH_LONG).show();
                        }
                        else{
                            databaseReference.child(mUser.getUid()).child("companyReview").setValue(reviewMessage);
                            Toast.makeText(getActivity(),"Thank you, your review was recorded.",Toast.LENGTH_LONG).show();
                            review.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        // TODO: Use the ViewModel
    }

}
