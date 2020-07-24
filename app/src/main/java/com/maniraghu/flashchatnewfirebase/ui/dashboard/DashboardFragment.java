package com.maniraghu.flashchatnewfirebase.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling.CarPool;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.CorporateForum.CorpForum;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.FeelingLonely.FeelingLonely;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity.JobSecurity;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.PartnerWithUs.Partner;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating.RateFragment;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.SmilePlease.SmilePleaseViewer;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.WellBeingCorner.WellBeing;
import com.maniraghu.flashchatnewfirebase.ui.dashboard.WomensCorner.WomenCorner;

public class DashboardFragment extends BaseFragment {

    private DashboardViewModel dashboardViewModel;
    public CardView cvrate,cvpool,cvwell,cvwomen,cvforum,cvsmile,cvlone,cvsecurity,cvpartner;


    public DashboardFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        Bundle args=new Bundle();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Dashboard");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        cvrate=(CardView) view.findViewById(R.id.rate);
        cvrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new RateFragment(), null);
            }
        });
        cvpool=(CardView) view.findViewById(R.id.pooling);
        cvpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new CarPool(), null);
            }
        });
        cvwell=(CardView) view.findViewById(R.id.wellbeing);
        cvwell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new WellBeing(), null);
            }
        });
        cvlone=(CardView) view.findViewById(R.id.lonely);
        cvlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new FeelingLonely(), null);
            }
        });
        cvforum=(CardView) view.findViewById(R.id.corporate);
        cvforum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new CorpForum(), null);
            }
        });
        cvwomen=(CardView) view.findViewById(R.id.woman);
        cvwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new WomenCorner(), null);
            }
        });
        cvsmile=(CardView) view.findViewById(R.id.smile);
        cvsmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new SmilePleaseViewer(), null);
            }
        });
        cvsecurity=(CardView) view.findViewById(R.id.security);
        cvsecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new JobSecurity(), null);
            }
        });
        cvpartner=(CardView) view.findViewById(R.id.partner);
        cvpartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new Partner(), null);

            }
        });
    }
}