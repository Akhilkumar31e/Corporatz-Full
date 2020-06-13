package com.maniraghu.flashchatnewfirebase.ui.dashboard.Rating;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;


public class RatingFragment extends BaseFragment implements View.OnClickListener {

    private RatingViewModel mViewModel;
    String nameOfEmployee;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button submit,viewRatings;
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rating_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RatingViewModel.class);
        // TODO: Use the ViewModel
        databaseReference= FirebaseDatabase.getInstance().getReference("ratingInfo");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        submit=getActivity().findViewById(R.id.submit_rating);
        viewRatings=getActivity().findViewById(R.id.view_ratings);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database(view);
            }
        });
        RadioButton selfName= getView().findViewById(R.id.radioButton5);
        selfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askMe(view);
            }
        });
        RadioButton alias= getView().findViewById(R.id.radioButton6);
        alias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askMe(view);
            }
        });
        RadioButton RadioLeaderYes= getView().findViewById(R.id.radioLeaderYes);
        RadioLeaderYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioLeader(view);
            }
        });
        RadioButton RadioLeaderNo= getView().findViewById(R.id.radioLeaderNo);
        RadioLeaderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioLeader(view);
            }
        });
        RadioButton NoLeader= getView().findViewById(R.id.noLeader);
        NoLeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioLeader(view);
            }
        });
        RadioButton RadioYes= getView().findViewById(R.id.radioYes);
        RadioYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioInputMethod(view);
            }
        });
        RadioButton RadioNo= getView().findViewById(R.id.radioNo);
        RadioNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioInputMethod(view);
            }
        });

        Button b1=getView().findViewById(R.id.btn1);
        Button b2=getView().findViewById(R.id.btn2);
        Button b3=getView().findViewById(R.id.btn3);
        Button b4=getView().findViewById(R.id.btn4);
        Button b5=getView().findViewById(R.id.btn5);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);

        viewRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity != null) mActivity.navigateToFragment(new AllRatingsFragment(), null);
            }
        });

    }

    public void askMe(View view){
        LayoutInflater li=getLayoutInflater();
        View promptsView=li.inflate(R.layout.prompts,null);
        final AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
        builder.setView(promptsView);
        final EditText nameBox=(EditText)promptsView.findViewById(R.id.editText2);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nameOfEmployee=nameBox.getText().toString();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();



    }
    public void takeNum(View view){
        int q1=0;
        switch (view.getId()){

            case R.id.btn1:

                Button b1=(Button)view.findViewById(R.id.btn1);
                q1= Integer.parseInt(b1.getText().toString());
                Log.d("sample q1", "value of q1 "+q1);
                //store the "q1" int database
                break;
            case R.id.btn2:

                Button b2=(Button)view.findViewById(R.id.btn2);
                q1= Integer.parseInt(b2.getText().toString());
                Log.d("sample q1", "value of q1 "+q1);
                //store the "q1" int database
                break;

            case R.id.btn3:

                Button b3=(Button)view.findViewById(R.id.btn3);
                q1= Integer.parseInt(b3.getText().toString());
                Log.d("sample q1", "value of q1 "+q1);
                //store the "q1" int database
                break;
            case R.id.btn4:

                Button b4=(Button)view.findViewById(R.id.btn4);
                q1= Integer.parseInt(b4.getText().toString());
                Log.d("sample q1", "value of q1 "+q1);
                //store the "q1" int database
                break;
            case R.id.btn5:

                Button b5=(Button)view.findViewById(R.id.btn5);
                q1= Integer.parseInt(b5.getText().toString());
                Log.d("sample q1", "value of q1 "+q1);
                //store the "q1" int database
                break;
        }
    }
    public void radioInputMethod(View view){
        String input="default";
        switch (view.getId()){
            case R.id.radioYes:
                RadioButton rbtn=(RadioButton)view.findViewById(R.id.radioYes);
                input=rbtn.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: "+input);
                break;
            case R.id.radioNo:
                RadioButton rbtnNo=(RadioButton)view.findViewById(R.id.radioNo);
                input=rbtnNo.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: "+input);
                break;
        }
    }
    public void radioLeader(View view){
        String input="default";
        switch (view.getId()){
            case R.id.radioLeaderYes:
                RadioButton rbtn=(RadioButton)view.findViewById(R.id.radioLeaderYes);
                input=rbtn.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: "+input);
                break;
            case R.id.radioLeaderNo:
                RadioButton rbtnNo=(RadioButton)view.findViewById(R.id.radioLeaderNo);
                input=rbtnNo.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: "+input);
                break;
            case R.id.noLeader:
                RadioButton noLeader=(RadioButton)view.findViewById(R.id.noLeader);
                input=noLeader.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: "+input);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        takeNum(view);
    }

    public void database(View view){
        RatingBar rating=(RatingBar)getActivity().findViewById(R.id.ratingBar);
        //add rating to database
        final TextView comName=(TextView) getActivity().findViewById(R.id.tvCom);
        final TextView comRating=(TextView)getActivity().findViewById(R.id.finalView);
        final EditText name=(EditText)getActivity().findViewById(R.id.companyName);
        final String companyName=name.getText().toString();


        //add companyName to database
        final Float ratingVal=rating.getRating();



        final Rating r=new Rating(companyName,ratingVal.toString(),mUser.getUid(),"");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(mUser.getUid())){
                    Rating currRating=dataSnapshot.child(mUser.getUid()).getValue(Rating.class);
                    comName.setText(currRating.getCompanyName());
                    comRating.setText(currRating.getCompanyRating());
                    Toast.makeText(getActivity(),"You have already submitted your response",Toast.LENGTH_LONG).show();
                }
                else{
                    comName.setText(companyName);
                    Log.d("companyName",companyName);
                    Log.d("rating",ratingVal+"");

                    comRating.setText(ratingVal+"");
                    databaseReference.child(mUser.getUid()).setValue(r);
                    Toast.makeText(getActivity(),"Successfully posted your rating",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
