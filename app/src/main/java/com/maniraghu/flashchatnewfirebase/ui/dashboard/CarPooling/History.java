package com.maniraghu.flashchatnewfirebase.ui.dashboard.CarPooling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class History extends RecyclerView.Adapter<History.HistoryViewHolder> {
    private Context mContext;
    private List<RideInformation> mList;
    private DatabaseReference mDatabase,reference,deleteDatabase;
    public History(Context mContext, List<RideInformation> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.history_items,parent,false);
        return new History.HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryViewHolder holder, int position) {
        final RideInformation current=mList.get(position);
        mDatabase= FirebaseDatabase.getInstance().getReference("history");
        final String ride_key=current.getRideId();
        reference=mDatabase.child(current.getUserId()).child(ride_key);
        deleteDatabase=FirebaseDatabase.getInstance().getReference("poolInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("status").getValue().toString().equals("open")){
                    holder.close.setText("Close");
                }
                else
                    holder.close.setText("Closed");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.destination.setText("To : "+current.getDestination());
        holder.source.setText("Starting at: "+current.getSource());

        holder.price.setText("Price : "+current.getCost()+"/-");
        holder.time.setText("Time : "+current.getTime());
        final String call=current.getMobile();
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDatabase.child(ride_key).removeValue();
                Map<String,Object> hm=new HashMap<>();
                hm.put("status","closed");
                reference.updateChildren(hm);
               holder.close.setText("Closed");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView source,destination,price,time;
        Button close;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            source=itemView.findViewById(R.id.history_source);
            destination=itemView.findViewById(R.id.history_destination);
            price=itemView.findViewById(R.id.history_price);
            close=itemView.findViewById(R.id.closeRideButton);
            time=itemView.findViewById(R.id.history_time);
        }
    }
}
