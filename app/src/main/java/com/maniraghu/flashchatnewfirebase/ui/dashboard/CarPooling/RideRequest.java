package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

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

public class RideRequest extends RecyclerView.Adapter<RideRequest.RideRequestViewHolder> {
    private Context mContext;
     private List<RideInformation> mRideInfo;

    public RideRequest(Context mContext, List<RideInformation> mRideInfo) {
        this.mContext = mContext;
        this.mRideInfo = mRideInfo;
    }

    @NonNull
    @Override
    public RideRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.ride_search_items,parent,false);
        return new RideRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RideRequestViewHolder holder, int position) {
            final RideInformation current=mRideInfo.get(position);

            holder.destination.setText(current.getDestination());
            holder.source.setText(current.getSource());
            holder.riderName.setText(current.getNameOfPooler());
            holder.gender.setText(current.getGender());
            holder.price.setText("Price : "+current.getCost()+"/-");
            holder.time.setText("Time : "+current.getTime());
            final String call=current.getMobile();
            holder.mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent=new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+call.toString()));
                    mContext.startActivity(callIntent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return mRideInfo.size();
    }

    public class RideRequestViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView riderName,gender,source,destination,price,time;
        Button mobile;
        public RideRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            riderName=itemView.findViewById(R.id.rider_name);
            gender=itemView.findViewById(R.id.rider_gender);
            source=itemView.findViewById(R.id.request_source_item);
            destination=itemView.findViewById(R.id.request_destination_item);
            price=itemView.findViewById(R.id.ride_price);
            mobile=itemView.findViewById(R.id.call_rider);
            time=itemView.findViewById(R.id.ride_time);
        }
    }
}
