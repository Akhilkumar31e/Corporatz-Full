package com.maniraghu.flashchatnewfirebase;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment implements FragmentListener {
    public EventListener mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mActivity = (EventListener) context;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // clear fragment
        mActivity = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set fragment
        mActivity.setFragment(this);
    }

    @Override
    public boolean backPressed() {
        return false;
    }
}