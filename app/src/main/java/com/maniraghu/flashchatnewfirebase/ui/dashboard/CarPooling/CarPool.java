package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maniraghu.flashchatnewfirebase.R;

public class CarPool extends Fragment {

    private CarPoolViewModel mViewModel;

    public static CarPool newInstance() {
        return new CarPool();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.car_pool_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CarPoolViewModel.class);
        // TODO: Use the ViewModel
    }

}
