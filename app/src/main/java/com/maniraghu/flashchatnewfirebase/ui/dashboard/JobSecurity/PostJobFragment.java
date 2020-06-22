package com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;

public class PostJobFragment extends BaseFragment {

    private PostJobViewModel mViewModel;
    private AutoCompleteTextView job_name,company_name,salary,contact;

    private Button post;

    private DatabaseReference mDatabase;

    public static PostJobFragment newInstance() {
        return new PostJobFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_job_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostJobViewModel.class);
        // TODO: Use the ViewModel
        mDatabase= FirebaseDatabase.getInstance().getReference().child("jobInfo");
        job_name=getActivity().findViewById(R.id.post_job_name);
        company_name= getActivity().findViewById(R.id.post_company_name);
        salary=getActivity().findViewById(R.id.post_salary);
        contact=getActivity().findViewById(R.id.post_job_contact);
        post=getActivity().findViewById(R.id.post_job_button);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobInfo newJob= new JobInfo(job_name.getText().toString(),company_name.getText().toString(),salary.getText().toString(),contact.getText().toString());
                String uploadId=mDatabase.push().getKey();
                mDatabase.child(uploadId).setValue(newJob);
                Toast.makeText(getContext(),"Job posted successfully",Toast.LENGTH_LONG).show();
                if(mActivity!=null) mActivity.navigateToFragment(new JobSecurity(),null);
            }
        });
    }

}
