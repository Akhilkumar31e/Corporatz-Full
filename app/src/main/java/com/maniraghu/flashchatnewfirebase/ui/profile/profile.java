package com.maniraghu.flashchatnewfirebase.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.profile.AboutUsFragment.AboutUsFragment;
import com.maniraghu.flashchatnewfirebase.ui.profile.ContactUsFragment.ContactUsFragment;
import com.maniraghu.flashchatnewfirebase.ui.profile.FAQFragment.FAQFragment;
import com.maniraghu.flashchatnewfirebase.ui.profile.SubscriptionPlans.SubscriptionPlans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class profile extends BaseFragment {

    private ProfileViewModel mViewModel;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    public DatabaseReference myRef;
    private String userId;
    public String userName;
    Button logout,faq,contact,about,subscription;
    private ExpandableListView listView;
    private ExpandableList listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    public TextView tvUser;
    private  TextView comReg;
    public  FirebaseUser user;
    public static profile newInstance() {
        return new profile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference("Users");
        user=mAuth.getCurrentUser();
        userId=user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               read(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        faq=getActivity().findViewById(R.id.FAQs_button);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new FAQFragment(), null);
            }
        });
        contact=getActivity().findViewById(R.id.ContactUs_butotn);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new ContactUsFragment(), null);
            }
        });
        about=getActivity().findViewById(R.id.AboutUs_button);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new AboutUsFragment(), null);
            }
        });
        subscription=getActivity().findViewById(R.id.subscription_plans_button);
        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new SubscriptionPlans(), null);
            }
        });

        logout = getActivity().findViewById(R.id.logout_button);
        tvUser=getActivity().findViewById(R.id.username);
        comReg=getActivity().findViewById(R.id.company);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
        listView= (ExpandableListView)getActivity().findViewById(R.id.expand);
        initData();
        listAdapter = new ExpandableList(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }
    public void out(){
        String res="Logged Out Successfully";
        mAuth.signOut();
        Intent i=new Intent();
        i.putExtra("message",res);
       getActivity().setResult(RESULT_OK,i);
        getActivity().finish();
    }
    private  void initData(){
        listDataHeader= new ArrayList<>();
        listHash=new HashMap<>();

        listDataHeader.add("Skills");
        listDataHeader.add("Certifications");

        List<String>  skills = new ArrayList<>();
        skills.add("C++");
        skills.add("Java");
        skills.add("Python");
        skills.add("Web Tech");

        List<String> certificates =new ArrayList<>();
        certificates.add("Nptel python");

        listHash.put(listDataHeader.get(0),skills);
        listHash.put(listDataHeader.get(1),certificates);

    }

    private void read(DataSnapshot ds){

            UserInformation userInformation=new UserInformation();
            if(ds.child(userId).exists()) {
                userInformation.setUsername(ds.child(userId).getValue(UserInformation.class).getUsername());
                userInformation.setCompanyname(ds.child(userId).getValue(UserInformation.class).getCompanyname());
                userInformation.setRegion(ds.child(userId).getValue(UserInformation.class).getRegion());


                if (userInformation.getUsername() != null) {
                    tvUser.setText(userInformation.getUsername());
                    userName = userInformation.getUsername();
                }
                else {
                    Intent in = getActivity().getIntent();
                    String email = in.getStringExtra("email");
                    userName=email;
                    tvUser.setText(email);
                }

                comReg.setText(userInformation.getCompanyname() + "," + userInformation.getRegion());
            }

    }
}
