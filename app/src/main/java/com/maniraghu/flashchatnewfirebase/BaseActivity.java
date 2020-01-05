package com.maniraghu.flashchatnewfirebase;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class BaseActivity extends AppCompatActivity implements EventListener {
    private FragmentListener mFragment;

    @Override
    public void navigateToFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);

        ft.addToBackStack(fragment.getTag());
        ft.commit();
    }

    public void addFragmentToContainer(Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

    @Override
    public void setFragment(Fragment fragment) {
        try {
            mFragment = (FragmentListener) fragment;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mFragment.backPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            }
            else {
                super.onBackPressed();
            }
        }
    }
}
