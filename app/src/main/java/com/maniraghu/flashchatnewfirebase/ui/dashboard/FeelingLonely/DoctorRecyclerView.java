package com.maniraghu.flashchatnewfirebase.ui.dashboard.FeelingLonely;

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

public class DoctorRecyclerView extends RecyclerView.Adapter<DoctorRecyclerView.DoctorViewHolder> {
    private Context mContext;
    private List<Doctor> mList;

    public DoctorRecyclerView(Context mContext, List<Doctor> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.doctor_list_items,parent,false);
        return new DoctorRecyclerView.DoctorViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorViewHolder holder, int position) {
        final Doctor doctor=mList.get(position);
        holder.doctorName.setText(doctor.getName());
        holder.qualification.setText(doctor.getQualification());
        holder.hospital.setText(doctor.getHospital());
        holder.timings.setText(doctor.getTimings());
        holder.callDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call=new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+doctor.getMobile()));
                mContext.startActivity(call);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  class DoctorViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView doctorName;
        public TextView qualification;
        public TextView hospital;
        public TextView timings;
        public Button callDoctor;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            doctorName = itemView.findViewById(R.id.item_doctor_name);
            qualification = itemView.findViewById(R.id.item_qualification);
            hospital = itemView.findViewById(R.id.item_hospital);
            timings = itemView.findViewById(R.id.item_timings);
            callDoctor = itemView.findViewById(R.id.item_doctor_call);
        }
    }
}
