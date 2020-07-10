package com.maniraghu.flashchatnewfirebase.ui.dashboard.FeelingLonely;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maniraghu.flashchatnewfirebase.R;

public class DoctorForm extends Fragment {

    private DoctorFormViewModel mViewModel;
    private AutoCompleteTextView name,area,qualification,hospital,address,timings,mobile;
    Button submit;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public FirebaseUser currUser;
    public static DoctorForm newInstance() {
        return new DoctorForm();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doctor_form_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DoctorFormViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Fill the form");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=getActivity().findViewById(R.id.doctorName);
        area=getActivity().findViewById(R.id.areaOfSpeciality);
        qualification=getActivity().findViewById(R.id.doctor_qualification);
        hospital=getActivity().findViewById(R.id.doctor_hospital);
        address=getActivity().findViewById(R.id.doctor_hospital_address);
        timings=getActivity().findViewById(R.id.doctor_hospital_timings);
        mobile=getActivity().findViewById(R.id.doctor_mobile);
        submit=getActivity().findViewById(R.id.doctor_submit_button);
        mDatabase = FirebaseDatabase.getInstance().getReference("doctorsInfo");
        mAuth=FirebaseAuth.getInstance();
        currUser=mAuth.getCurrentUser();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doctor d=new Doctor(currUser.getUid(),name.getText().toString(),area.getText().toString(),qualification.getText().toString(),hospital.getText().toString(),address.getText().toString(),timings.getText().toString(),mobile.getText().toString());
                String id=mDatabase.push().getKey();
                mDatabase.child(id).setValue(d);
            }
        });
    }

}
