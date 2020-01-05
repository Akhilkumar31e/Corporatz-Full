package com.maniraghu.flashchatnewfirebase.ui.dashboard.WellBeingCorner;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maniraghu.flashchatnewfirebase.R;

public class WellBeing extends Fragment {

    private WellBeingViewModel mViewModel;

    public static WellBeing newInstance() {
        return new WellBeing();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.well_being_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WellBeingViewModel.class);
        // TODO: Use the ViewModel
    }

}
