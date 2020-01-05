package com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maniraghu.flashchatnewfirebase.R;

public class Smile extends Fragment {

    private SmileViewModel mViewModel;

    public static Smile newInstance() {
        return new Smile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SmileViewModel.class);
        // TODO: Use the ViewModel
    }

}
