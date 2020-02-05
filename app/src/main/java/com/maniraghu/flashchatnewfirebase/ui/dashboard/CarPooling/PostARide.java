package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maniraghu.flashchatnewfirebase.R;

public class PostARide extends Fragment {

    private PostArideViewModel mViewModel;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private AutoCompleteTextView username;
    private RadioGroup freeOrDonate;
    private RadioGroup gender;
    private RadioGroup CarOrBike;
    private RadioButton radioFOD;
    private RadioButton radioGender;
    private RadioButton radioCOB;
    private AutoCompleteTextView price;
    private AutoCompleteTextView source;
    private  AutoCompleteTextView destination;
    private AutoCompleteTextView mobile;
    private Button post;
    private TimePicker time;
    public FirebaseUser currUser;
    public static PostARide newInstance() {
        return new PostARide();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_aride_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostArideViewModel.class);
        // TODO: Use the ViewModel
        mDatabase = FirebaseDatabase.getInstance().getReference("poolInfo");
        mAuth=FirebaseAuth.getInstance();
        currUser=mAuth.getCurrentUser();
        username=(AutoCompleteTextView)getActivity().findViewById(R.id.nameOfPooler);
        freeOrDonate=(RadioGroup)getActivity().findViewById(R.id.radio_freeOrNot);

        gender=(RadioGroup)getActivity().findViewById(R.id.radio_gender);

        CarOrBike=(RadioGroup)getActivity().findViewById(R.id.radio_type);

        price=(AutoCompleteTextView)getActivity().findViewById(R.id.amountForRide);
        source=(AutoCompleteTextView)getActivity().findViewById(R.id.source);
        destination=(AutoCompleteTextView)getActivity().findViewById(R.id.destination);
        time=(TimePicker)getActivity().findViewById(R.id.time);
        mobile=(AutoCompleteTextView)getActivity().findViewById(R.id.mobile);
        post=(Button)getActivity().findViewById(R.id.post_ride_button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected=freeOrDonate.getCheckedRadioButtonId();
                radioFOD=(RadioButton)getActivity().findViewById(selected);
                selected=gender.getCheckedRadioButtonId();
                radioGender=(RadioButton)getActivity().findViewById(selected);
                selected=CarOrBike.getCheckedRadioButtonId();
                radioCOB=(RadioButton)getActivity().findViewById(selected);
                String t=time.getCurrentHour()+":"+time.getCurrentMinute();
                Ride ride=new Ride(currUser.getUid(),username.getText().toString(),radioFOD.getText().toString(),price.getText().toString(),radioGender.getText().toString(),radioCOB.getText().toString(),source.getText().toString(),destination.getText().toString(),t,mobile.getText().toString());
                String uploadId=mDatabase.push().getKey();
                mDatabase.child(uploadId).setValue(ride);
                Toast.makeText(getActivity(),"Successfully posted your ride",Toast.LENGTH_LONG).show();
            }
        });
    }

}
