package com.maniraghu.flashchatnewfirebase.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.BaseFragment;
import com.maniraghu.flashchatnewfirebase.R;
import com.maniraghu.flashchatnewfirebase.ui.profile.UserInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class HomeFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private NewsFeed mNewsFeed;
    private RecyclerView recyclerView,searchRecylerView;
    private DatabaseReference mDatabase,mUserDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private List<NewsInformation> mNews;
    private EditText search;
    private ImageButton searchButton;
    private List<SearchUserInfo> searchResults;
    private SearchRecyclerView searchHolder;
    String post_title;
    String post_desc;
    String post_img;

        private ProgressBar mProgressCircle;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Corporatz");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerview);
        search=getActivity().findViewById(R.id.search_people);
        searchRecylerView=getActivity().findViewById(R.id.search_recyclerview);
        searchRecylerView.setHasFixedSize(true);
        searchRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchButton=getActivity().findViewById(R.id.search_button);

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){

                    searchResults=new ArrayList<>();
                    searchHolder=new SearchRecyclerView(getContext(),searchResults);
                    searchRecylerView.setAdapter(searchHolder);
                }
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals(""))
               firebaseUserSearch(charSequence.toString());
                else{
                    search.clearFocus();
                    searchResults=new ArrayList<>();
                    searchHolder=new SearchRecyclerView(getContext(),searchResults);
                    searchRecylerView.setAdapter(searchHolder);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager((getContext())));
        recyclerView.setHasFixedSize(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("newsfeed");
        mAuth=FirebaseAuth.getInstance();
        mNews=new ArrayList<>();
        mProgressCircle=getActivity().findViewById(R.id.progress_circle);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    NewsInformation newsInformation=postSnapshot.getValue(NewsInformation.class);

                    mNews.add(newsInformation);
                }
                Collections.reverse(mNews);
                mNewsFeed=new NewsFeed(getContext(),mNews);
                recyclerView.setAdapter(mNewsFeed);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Query firebaseQuery = mUserDatabase.orderByChild("username").startAt(searchText).endAt(searchText+"\uf8ff");
        searchResults=new ArrayList<>();
        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserInformation userResult = ds.getValue(UserInformation.class);
                    SearchUserInfo result = new SearchUserInfo(userResult.getUsername(),ds.getKey());
                    searchResults.add(result);
                }
                searchHolder=new SearchRecyclerView(getContext(),searchResults);
                searchRecylerView.setAdapter(searchHolder);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /* FirebaseRecyclerAdapter<UserInformation,UsersViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<UserInformation, UsersViewHolder>(
                UserInformation.class,
                R.layout.search_list_items,
                UsersViewHolder.class,
                mUserDatabase
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder usersViewHolder, UserInformation userInformation, int i) {
                usersViewHolder.setDetails(getContext(),userInformation.getUsername());
            }
        };

        searchRecylerView.setAdapter(firebaseRecyclerAdapter);*/
    }

    /*public static  class UsersViewHolder extends  RecyclerView.ViewHolder {
        View  mView;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mView= itemView;
        }

        public void setDetails(Context mContext, String username){
           TextView userTextView = mView.findViewById(R.id.search_item_username);
           userTextView.setText(username);
        }


    }*/




}