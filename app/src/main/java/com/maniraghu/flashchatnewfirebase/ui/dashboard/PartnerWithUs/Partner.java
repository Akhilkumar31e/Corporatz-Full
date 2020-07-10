package com.maniraghu.flashchatnewfirebase.ui.dashboard.PartnerWithUs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

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

public class Partner extends Fragment {

    private PartnerViewModel mViewModel;
    AutoCompleteTextView companyName,cEmail,cMobile,cTime;
    Button submit;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public static Partner newInstance() {
        return new Partner();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.partner_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PartnerViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Fill the form");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        companyName=getActivity().findViewById(R.id.partner_company_name);
        cEmail=getActivity().findViewById(R.id.partner_email_id);
        cMobile=getActivity().findViewById(R.id.partner_mobile);
        cTime=getActivity().findViewById(R.id.partner_time);
        submit=getActivity().findViewById(R.id.partner_submit_button);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("PartnerWithUs");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PartnerInformation newEntry=new PartnerInformation(companyName.getText().toString(),cEmail.getText().toString(),cMobile.getText().toString(),cTime.getText().toString());
                mDatabase.child(mUser.getUid()).setValue(newEntry);
                Toast.makeText(getActivity(),"Thank You,We will contact you soon...",Toast.LENGTH_LONG).show();

            }
        });

    }

}
