package com.maniraghu.flashchatnewfirebase.ui.profile.SubscriptionPlans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.maniraghu.flashchatnewfirebase.R;

public class SubscriptionPlans extends Fragment {

    private SubscriptionPlansViewModel mViewModel;

    public static SubscriptionPlans newInstance() {
        return new SubscriptionPlans();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(SubscriptionPlansViewModel.class);
        View root = inflater.inflate(R.layout.subscription_plans_fragment, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubscriptionPlansViewModel.class);
        // TODO: Use the ViewModel

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Subscription Plans(Coming Soon)");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
