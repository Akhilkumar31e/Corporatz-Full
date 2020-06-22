package com.maniraghu.flashchatnewfirebase.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;

public class EditProfileFragment extends BaseFragment {

    private EditProfileViewModel mViewModel;
    private AutoCompleteTextView username,companyName,companyEmail,dob,region,skillsField,certificatesField;
    private Button submit;
    private DatabaseReference mUserDatabase,mSkillsDatabase,mCertificatesDatabase;
    private FirebaseAuth mAuth;


    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);
        // TODO: Use the ViewModel
        username=getActivity().findViewById(R.id.edit_profile_username);
        companyName=getActivity().findViewById(R.id.edit_profile_compamy_name);
        companyEmail=getActivity().findViewById(R.id.edit_profile_compamy_email);
        dob=getActivity().findViewById(R.id.edit_profile_dob);
        region=getActivity().findViewById(R.id.edit_profile_region);
        submit=getActivity().findViewById(R.id.edit_profile_submit_button);
        skillsField=getActivity().findViewById(R.id.edit_profile_skills);
        certificatesField=getActivity().findViewById(R.id.edit_profile_certificates);
        mAuth=FirebaseAuth.getInstance();
        String mUser=mAuth.getCurrentUser().getUid();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser);
        mSkillsDatabase= FirebaseDatabase.getInstance().getReference().child("Skills").child(mUser);
        mCertificatesDatabase= FirebaseDatabase.getInstance().getReference().child("Certificates").child(mUser);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation currInfo=dataSnapshot.getValue(UserInformation.class);
                username.setText(currInfo.getUsername());
                companyName.setText(currInfo.getCompanyname());
                region.setText(currInfo.getRegion());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mSkillsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Skills currSkills=dataSnapshot.getValue(Skills.class);
                if(currSkills!=null) skillsField.setText(currSkills.getSkillsdata());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mCertificatesDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Certificates currCerti=dataSnapshot.getValue(Certificates.class);
                if(currCerti!=null) certificatesField.setText(currCerti.getCertificateData());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation updated=new UserInformation(username.getText().toString(),companyName.getText().toString(),region.getText().toString());
                mUserDatabase.setValue(updated);
                Skills updatedSkills=new Skills(skillsField.getText().toString());
                mSkillsDatabase.setValue(updatedSkills);
                Certificates updatedCerti=new Certificates(certificatesField.getText().toString());
                mCertificatesDatabase.setValue(updatedCerti);
                Toast.makeText(getContext(),"Changes made successfully",Toast.LENGTH_LONG).show();
                if (mActivity != null) mActivity.navigateToFragment(new profile(), null);
            }
        });
    }

}
