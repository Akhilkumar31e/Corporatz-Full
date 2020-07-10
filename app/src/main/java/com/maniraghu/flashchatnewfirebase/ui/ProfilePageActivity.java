package com.maniraghu.flashchatnewfirebase.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.profile.Certificates;
import com.maniraghu.flashchatnewfirebase.ui.profile.ExpandableList;
import com.maniraghu.flashchatnewfirebase.ui.profile.Skills;
import com.maniraghu.flashchatnewfirebase.ui.profile.UserInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {
    private TextView username;
    private TextView place,followers,following;;
    private Button follow;
    private DatabaseReference userProfile,people;
    public DatabaseReference skillsDatabase,certiDatabase;
    private FirebaseAuth mAuth;
    private ExpandableListView listView;
    private ExpandableList listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private boolean processFollow;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //Toolbar settings
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username=findViewById(R.id.profile_username);
        place=findViewById(R.id.profile_company);
        follow=findViewById(R.id.profile_follow_button);
        followers=findViewById(R.id.profile_followers);
        following=findViewById(R.id.profile_following);
        listView= (ExpandableListView)findViewById(R.id.profile_expand);


        final String userId=getIntent().getExtras().getString("userid");

        skillsDatabase=FirebaseDatabase.getInstance().getReference().child("Skills").child(userId);
        certiDatabase=FirebaseDatabase.getInstance().getReference().child("Certificates").child(userId);
        initData();
        listAdapter = new ExpandableList(this,listDataHeader,listHash);

        listView.setAdapter(listAdapter);
        userProfile= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        final String currUserId=mAuth.getInstance().getCurrentUser().getUid();
        people  = FirebaseDatabase.getInstance().getReference().child("people");
        if(userId.equals(currUserId)){
            follow.setText("ME");
            follow.setEnabled(false);
        }
        userProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final UserInformation user=dataSnapshot.getValue(UserInformation.class);
                username.setText(user.getUsername());
                getSupportActionBar().setTitle(user.getUsername());
                place.setText(user.getCompanyname()+","+user.getRegion());
                people.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Following").child(currUserId).hasChild(userId)){
                            follow.setText("Following");
                            follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            follow.setTextColor(Color.parseColor("#000000"));
                        }
                        else{
                            if(userId.equals(currUserId)){
                                follow.setText("ME");
                                follow.setEnabled(false);
                            }
                            else{
                                follow.setText("Follow");
                                follow.setBackgroundColor(Color.parseColor("#05C0F0"));
                                follow.setTextColor(Color.parseColor("#FFFFFF"));
                            }
                        }
                        followers.setText(String.valueOf(dataSnapshot.child("Followers").child(userId).getChildrenCount()));
                        following.setText(String.valueOf(dataSnapshot.child("Following").child(userId).getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                follow.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        processFollow=true;
                        people.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (processFollow) {
                                    if (dataSnapshot.child("Following").child(currUserId).hasChild(userId)) {
                                        follow.setText("Follow");
                                        follow.setBackgroundColor(Color.parseColor("#05C0F0"));
                                        follow.setTextColor(Color.parseColor("#FFFFFF"));
                                        people.child("Followers").child(userId).child(currUserId).removeValue();
                                        people.child("Following").child(currUserId).child(userId).removeValue();
                                        processFollow=false;
                                    } else {
                                        follow.setText("Following");
                                        follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                        follow.setTextColor(Color.parseColor("#05C0F0"));
                                        people.child("Followers").child(userId).child(currUserId).setValue("new");
                                        people.child("Following").child(currUserId).child(userId).setValue(user.getUsername());
                                        processFollow=false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void initData(){
        listDataHeader= new ArrayList<>();
        listHash=new HashMap<>();

        listDataHeader.add("Skills");
        listDataHeader.add("Certifications");

        skillsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Skills newSkills=dataSnapshot.getValue(Skills.class);
                List<String> items;
                if(newSkills!=null)  items = Arrays.asList(newSkills.getSkillsdata().split("\\s*,\\s*"));
                else{
                    items=new ArrayList<>();
                    items.add("NO skills were added by user");
                }
                listHash.put(listDataHeader.get(0),items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        certiDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Certificates newCerti=dataSnapshot.getValue(Certificates.class);
                List<String> items;
                if(newCerti!=null)  items = Arrays.asList(newCerti.getCertificateData().split("\\s*,\\s*"));
                else{
                    items=new ArrayList<>();
                    items.add("NO certificates were added by user");
                }
                listHash.put(listDataHeader.get(1),items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
