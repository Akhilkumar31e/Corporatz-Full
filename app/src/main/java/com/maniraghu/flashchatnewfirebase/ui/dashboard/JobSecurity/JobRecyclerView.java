package com.maniraghu.flashchatnewfirebase.ui.dashboard.JobSecurity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maniraghu.flashchatnewfirebase.R;

import java.util.List;

public class JobRecyclerView extends RecyclerView.Adapter<JobRecyclerView.JobViewHolder> {
    private Context mContext;
    private List<JobInfo> mList;

    public JobRecyclerView(Context mContext, List<JobInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.job_items,parent,false);
        return new JobRecyclerView.JobViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        final JobInfo jobs=mList.get(position);
        holder.jobName.setText(jobs.getJobName());
        holder.companyName.setText(jobs.getCompanyName());
        holder.salary.setText(jobs.getSalary());

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call=new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+jobs.getContact()));
                mContext.startActivity(call);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  class JobViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView jobName;
        public TextView companyName;
        public TextView salary;

        public Button contact;

        public JobViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            jobName = itemView.findViewById(R.id.job_name);
            companyName = itemView.findViewById(R.id.job_company_name);
            salary = itemView.findViewById(R.id.job_salary);
            contact = itemView.findViewById(R.id.job_contact);
        }
    }
}
