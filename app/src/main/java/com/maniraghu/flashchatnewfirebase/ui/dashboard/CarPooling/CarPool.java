package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating.RateFragment;

import java.util.ArrayList;
import java.util.List;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Car and bike Pooling");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager=(ViewPager)getActivity().findViewById(R.id.viewpagerForCarPool);
        setupViewPager(viewPager);
        TabLayout tabs=(TabLayout)getActivity().findViewById(R.id.carpool_tabs);
        tabs.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        RateFragment.Adapter adapter = new RateFragment.Adapter(getChildFragmentManager());
        adapter.addFragment(new PostARide(), "Post A Ride");
        adapter.addFragment(new RequestRideFragment(), "Request A Ride");
        adapter.addFragment(new HistoryFragment(),"History");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
