package com.maniraghu.flashchatnewfirebase.ui.dashboard.FeelingLonely;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease.SmilePleaseViewer;

public class FeelingLonely extends BaseFragment {

    private FeelingLonelyViewModel mViewModel;
    private Button doctor;
    private Button doctorList;
    private Button smilePlease;
    public static FeelingLonely newInstance() {
        return new FeelingLonely();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feeling_lonely_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FeelingLonelyViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Health Care 24x7");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doctor=getActivity().findViewById(R.id.areYouADoctorButton);
        doctorList=getActivity().findViewById(R.id.doctorsListButton);
        smilePlease=getActivity().findViewById(R.id.goToSmilePlease);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActivity!=null) mActivity.navigateToFragment(new DoctorForm(),null);
            }
        });
        doctorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActivity!=null) mActivity.navigateToFragment(new DoctorList(),null);
            }
        });
        smilePlease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mActivity!=null) mActivity.navigateToFragment(new SmilePleaseViewer(),null);
            }
        });
    }

}
