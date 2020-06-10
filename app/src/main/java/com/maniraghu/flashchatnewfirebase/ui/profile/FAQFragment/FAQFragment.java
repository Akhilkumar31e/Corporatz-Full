package com.maniraghu.flashchatnewfirebase.ui.profile.FAQFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.maniraghu.flashchatnewfirebase.R;

public class FAQFragment extends Fragment {

    private FAQViewModel mViewModel;

    public static FAQFragment newInstance() {
        return new FAQFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_a_q_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FAQViewModel.class);
        // TODO: Use the ViewModel

    }

}
