package com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maniraghu.flashchatnewfirebase.R;

public class JobSecurity extends Fragment {

    private JobSecurityViewModel mViewModel;

    public static JobSecurity newInstance() {
        return new JobSecurity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.job_security_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JobSecurityViewModel.class);
        // TODO: Use the ViewModel
    }

}
