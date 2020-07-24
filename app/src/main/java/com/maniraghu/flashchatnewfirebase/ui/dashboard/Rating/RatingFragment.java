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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
    String nameOfEmployee,dummyName;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button submit,viewRatings;
    int resArr[]=new int[100];
    int arrInd=0;
    int a1=0;
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

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Rate your Employer");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Button b6=getView().findViewById(R.id.btn6);
        Button b7=getView().findViewById(R.id.btn7);
        Button b8=getView().findViewById(R.id.btn8);
        Button b9=getView().findViewById(R.id.btn9);
        Button b10=getView().findViewById(R.id.btn10);
        Button b11=getView().findViewById(R.id.btn11);
        Button b12=getView().findViewById(R.id.btn12);
        Button b13=getView().findViewById(R.id.btn13);
        Button b14=getView().findViewById(R.id.btn14);
        Button b15=getView().findViewById(R.id.btn15);
        Button b16=getView().findViewById(R.id.btn16);
        Button b17=getView().findViewById(R.id.btn17);
        Button b18=getView().findViewById(R.id.btn18);
        Button b19=getView().findViewById(R.id.btn19);
        Button b20=getView().findViewById(R.id.btn20);
        Button b21=getView().findViewById(R.id.btn21);
        Button b22=getView().findViewById(R.id.btn22);
        Button b23=getView().findViewById(R.id.btn23);
        Button b24=getView().findViewById(R.id.btn24);
        Button b25=getView().findViewById(R.id.btn25);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b13.setOnClickListener(this);
        b14.setOnClickListener(this);
        b15.setOnClickListener(this);
        b16.setOnClickListener(this);
        b17.setOnClickListener(this);
        b18.setOnClickListener(this);
        b19.setOnClickListener(this);
        b20.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);
        b23.setOnClickListener(this);
        b24.setOnClickListener(this);
        b25.setOnClickListener(this);


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

    public Button findButton(int id){
        Button res=(Button)getActivity().findViewById(R.id.btn1);
        switch (id){
            case 1: res=(Button)getActivity().findViewById(R.id.btn1);
                break;
            case 2: res=(Button)getActivity().findViewById(R.id.btn2);
                break;
            case 3: res=(Button)getActivity().findViewById(R.id.btn3);
                break;
            case 4: res=(Button)getActivity().findViewById(R.id.btn4);
                break;
            case 5: res=(Button)getActivity().findViewById(R.id.btn5);
                break;
            case 6: res=(Button)getActivity().findViewById(R.id.btn6);
                break;
            case 7: res=(Button)getActivity().findViewById(R.id.btn7);
                break;
            case 8: res=(Button)getActivity().findViewById(R.id.btn8);
                break;
            case 9: res=(Button)getActivity().findViewById(R.id.btn9);
                break;
            case 10: res=(Button)getActivity().findViewById(R.id.btn10);
                break;
            case 11: res=(Button)getActivity().findViewById(R.id.btn11);
                break;
            case 12: res=(Button)getActivity().findViewById(R.id.btn12);
                break;
            case 13: res=(Button)getActivity().findViewById(R.id.btn13);
                break;
            case 14: res=(Button)getActivity().findViewById(R.id.btn14);
                break;
            case 15: res=(Button)getActivity().findViewById(R.id.btn15);
                break;
            case 16: res=(Button)getActivity().findViewById(R.id.btn16);
                break;
            case 17: res=(Button)getActivity().findViewById(R.id.btn17);
                break;
            case 18: res=(Button)getActivity().findViewById(R.id.btn18);
                break;
            case 19: res=(Button)getActivity().findViewById(R.id.btn19);
                break;
            case 20: res=(Button)getActivity().findViewById(R.id.btn20);
                break;
            case 21: res=(Button)getActivity().findViewById(R.id.btn21);
                break;
            case 22: res=(Button)getActivity().findViewById(R.id.btn22);
                break;
            case 23: res=(Button)getActivity().findViewById(R.id.btn23);
                break;
            case 24: res=(Button)getActivity().findViewById(R.id.btn24);
                break;
            case 25: res=(Button)getActivity().findViewById(R.id.btn25);
                break;

        }
        return res;
    }


    public void takeNum(View view){
        int q1=0;
        int floor=0;
        int ceil=0;
        Button res=(Button)view.findViewById(R.id.btn1);
        int idNum=0;
        switch (view.getId()) {

            case R.id.btn1:
                idNum=1;
                Button b1 = (Button) view.findViewById(R.id.btn1);
                res=(Button) view.findViewById(R.id.btn1);
                q1 = Integer.parseInt(b1.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn2:
                idNum=2;
                Button b2 = (Button) view.findViewById(R.id.btn2);
                res=(Button) view.findViewById(R.id.btn2);
                q1 = Integer.parseInt(b2.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                //store the "q1" int database
                break;

            case R.id.btn3:
                //add to database
                idNum=3;
                Button b3 = (Button) view.findViewById(R.id.btn3);
                res=(Button) view.findViewById(R.id.btn3);
                q1 = Integer.parseInt(b3.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                //store the "q1" int database
                break;
            case R.id.btn4:
                idNum=4;
                Button b4 = (Button) view.findViewById(R.id.btn4);
                res=(Button) view.findViewById(R.id.btn4);
                q1 = Integer.parseInt(b4.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                //store the "q1" int database
                break;
            case R.id.btn5:
                idNum=5;
                Button b5 = (Button) view.findViewById(R.id.btn5);
                res=(Button) view.findViewById(R.id.btn5);
                q1 = Integer.parseInt(b5.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn6:
                idNum=6;
                Button b6 = (Button) view.findViewById(R.id.btn6);
                res=(Button) view.findViewById(R.id.btn6);
                q1 = Integer.parseInt(b6.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn7:
                idNum=7;
                Button b7 = (Button) view.findViewById(R.id.btn7);
                res=(Button) view.findViewById(R.id.btn7);
                q1 = Integer.parseInt(b7.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn8:
                idNum=8;
                Button b8 = (Button) view.findViewById(R.id.btn8);
                res=(Button) view.findViewById(R.id.btn8);
                q1 = Integer.parseInt(b8.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn9:
                idNum=9;
                Button b9 = (Button) view.findViewById(R.id.btn9);
                res=(Button) view.findViewById(R.id.btn9);
                q1 = Integer.parseInt(b9.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn10:
                idNum=10;
                Button b10 = (Button) view.findViewById(R.id.btn10);
                res=(Button) view.findViewById(R.id.btn10);
                q1 = Integer.parseInt(b10.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn11:
                idNum=11;
                Button b11 = (Button) view.findViewById(R.id.btn11);
                res=(Button) view.findViewById(R.id.btn11);
                q1 = Integer.parseInt(b11.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn12:
                idNum=12;
                Button b12 = (Button) view.findViewById(R.id.btn12);
                res=(Button) view.findViewById(R.id.btn12);
                q1 = Integer.parseInt(b12.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn13:
                idNum=13;
                Button b13 = (Button) view.findViewById(R.id.btn13);
                res=(Button) view.findViewById(R.id.btn13);
                q1 = Integer.parseInt(b13.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn14:
                idNum=14;
                Button b14 = (Button) view.findViewById(R.id.btn14);
                res=(Button) view.findViewById(R.id.btn14);
                q1 = Integer.parseInt(b14.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn15:
                idNum=15;
                Button b15 = (Button) view.findViewById(R.id.btn15);
                res=(Button) view.findViewById(R.id.btn15);
                q1 = Integer.parseInt(b15.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn16:
                idNum=16;
                Button b16 = (Button) view.findViewById(R.id.btn16);
                res=(Button) view.findViewById(R.id.btn16);
                q1 = Integer.parseInt(b16.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn17:
                idNum=17;
                Button b17 = (Button) view.findViewById(R.id.btn17);
                res=(Button) view.findViewById(R.id.btn17);
                q1 = Integer.parseInt(b17.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn18:
                idNum=18;
                Button b18 = (Button) view.findViewById(R.id.btn18);
                res=(Button) view.findViewById(R.id.btn18);
                q1 = Integer.parseInt(b18.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn19:
                idNum=19;
                Button b19 = (Button) view.findViewById(R.id.btn19);
                res=(Button) view.findViewById(R.id.btn19);
                q1 = Integer.parseInt(b19.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn20:
                idNum=20;
                Button b20 = (Button) view.findViewById(R.id.btn20);
                res=(Button) view.findViewById(R.id.btn20);
                q1 = Integer.parseInt(b20.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn21:
                idNum=21;
                Button b21 = (Button) view.findViewById(R.id.btn21);
                res=(Button) view.findViewById(R.id.btn21);
                q1 = Integer.parseInt(b21.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn22:
                idNum=22;
                Button b22 = (Button) view.findViewById(R.id.btn22);
                res=(Button) view.findViewById(R.id.btn22);
                q1 = Integer.parseInt(b22.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn23:
                idNum=23;
                Button b23 = (Button) view.findViewById(R.id.btn23);
                res=(Button) view.findViewById(R.id.btn23);
                q1 = Integer.parseInt(b23.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn24:
                idNum=24;
                Button b24 = (Button) view.findViewById(R.id.btn24);
                res=(Button) view.findViewById(R.id.btn24);
                q1 = Integer.parseInt(b24.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;
            case R.id.btn25:
                idNum=25;
                Button b25 = (Button) view.findViewById(R.id.btn25);
                res=(Button) view.findViewById(R.id.btn25);
                q1 = Integer.parseInt(b25.getText().toString());
                Log.d("sample q1", "value of q1 " + q1);
                break;



        }
        resArr[arrInd++]=q1;
        floor=((idNum/5)*5)+1;
        if(idNum%5==0) floor=idNum-4;
        ceil=((idNum/5)+1)*5;
        if(idNum%5==0) ceil=idNum;
        for(int i=floor;i<=ceil;i++) {
            if(i!=idNum) {
                Button btn = (Button) findButton(i);
                btn.setBackgroundResource(0);
            }
        }
        res.setBackgroundResource(R.drawable.setcolorbackground);

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
        if(input.equalsIgnoreCase("yes")){
            resArr[arrInd++]=-1;
        }
        else{
            resArr[arrInd++]=-2;
        }
    }
    public void radioLeader(View view){
        String input = "default";
        switch (view.getId()) {
            case R.id.radioLeaderYes:
                RadioButton rbtn = (RadioButton) view.findViewById(R.id.radioLeaderYes);
                input = rbtn.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: " + input);
                break;
            case R.id.radioLeaderNo:
                RadioButton rbtnNo = (RadioButton) view.findViewById(R.id.radioLeaderNo);
                input = rbtnNo.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: " + input);
                break;
            case R.id.noLeader:
                RadioButton noLeader = (RadioButton) view.findViewById(R.id.noLeader);
                input = noLeader.getText().toString();
                //store input value in dataBase
                Log.d("radio", "radioInputMethod: " + input);
                break;
        }
        if(input.equalsIgnoreCase("yes")){
            resArr[arrInd++]=-1;
        }
        else if(input.equalsIgnoreCase("no")){
            resArr[arrInd++]=-2;
        }
        else{
            resArr[arrInd++]=-3;
        }
    }





    public void database(View view) {
        RatingBar rating = (RatingBar) getActivity().findViewById(R.id.ratingBar);
        //add rating to database
        //final TextView comName = (TextView) getActivity().findViewById(R.id.tvCom);
        //final TextView comRating = (TextView) getActivity().findViewById(R.id.finalView);
        final EditText name = (EditText) getActivity().findViewById(R.id.companyName);
        final String companyName = name.getText().toString();
        boolean flag = true;
        int cou = 0;

            //add companyName to database
            final Float ratingVal = rating.getRating();


            final Rating r = new Rating(companyName, ratingVal.toString(), mUser.getUid(), "");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(mUser.getUid())) {
                        Rating currRating = dataSnapshot.child(mUser.getUid()).getValue(Rating.class);
                        //comName.setText(currRating.getCompanyName());
                        //comRating.setText(currRating.getCompanyRating());
                        Toast.makeText(getActivity(), "You have already submitted your response", Toast.LENGTH_LONG).show();
                    } else {
                        //comName.setText(companyName);
                        Log.d("companyName", companyName);
                        Log.d("rating", ratingVal + "");

                       // comRating.setText(ratingVal + "");
                        databaseReference.child(mUser.getUid()).setValue(r);
                        Toast.makeText(getActivity(), "Successfully posted your rating", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    @Override
    public void onClick(View view) {
        takeNum(view);
    }
}
