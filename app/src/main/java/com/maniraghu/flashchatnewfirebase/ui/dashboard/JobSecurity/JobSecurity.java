package com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobSecurity extends BaseFragment {

    private JobSecurityViewModel mViewModel;
    private JobRecyclerView jobView;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<JobInfo> mJobs;
    private ProgressBar progressCircle;
    private TextView noJob;
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
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.job_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("jobInfo");
        noJob=getActivity().findViewById(R.id.no_jobs_msg);
        mJobs=new ArrayList<>();
        progressCircle=getActivity().findViewById(R.id.job_progress_circle);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    JobInfo jb=ds.getValue(JobInfo.class);
                    mJobs.add(jb);
                }
                if(mJobs.size()==0){
                    noJob.setText("No jobs available at the moment");
                }
                else {
                    Collections.reverse(mJobs);
                    jobView = new JobRecyclerView(getContext(), mJobs);
                    recyclerView.setAdapter(jobView);
                }
                progressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
