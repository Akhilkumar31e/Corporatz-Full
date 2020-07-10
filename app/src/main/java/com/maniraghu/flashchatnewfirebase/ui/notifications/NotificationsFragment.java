package com.maniraghu.flashchatnewfirebase.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maniraghu.flashchatnewfirebase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter adapter;
    private SimpleSectionedRecyclerViewAdapter sectionedAdapter;
    private List<Notification> dataset;
    private List<SimpleSectionedRecyclerViewAdapter.Section> sections;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setTitle("Notifications");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mAuth=FirebaseAuth.getInstance();
        String currUser=mAuth.getCurrentUser().getUid();
        dataset = new ArrayList<>();

        recyclerView = getActivity().findViewById(R.id.noti_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        adapter = new MyAdapter(getContext(), dataset);

        sections = new ArrayList<>();

        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        sectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getContext(), R.layout.section, R.id.section_text, adapter);
        sectionedAdapter.setSections(sections.toArray(dummy));

        recyclerView.setAdapter(sectionedAdapter);

        db = FirebaseDatabase.getInstance().getReference("notifications").child(currUser);

        getData();
    }

    private void getData() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notification> dataset = new ArrayList<>();
                HashMap<String, Integer> uniq = new HashMap<>();
                List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
                int i=0;
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    final Notification notification=ds.getValue(Notification.class);
                    dataset.add(notification);

                    if(!uniq.containsKey(notification.getDateString())) {
                        uniq.put(notification.getDateString(), i);
                    }
                    i++;
                }
                Notification welcome = new Notification("Welcome to Corporatz","Thank you for joining us.");
                dataset.add(welcome);
                if(!uniq.containsKey(welcome.getDateString())) {
                    uniq.put(welcome.getDateString(), i);
                }
                i++;
                for(String key: uniq.keySet()) {
                    sections.add(new SimpleSectionedRecyclerViewAdapter.Section(uniq.get(key), key));
                }

                updateData(dataset, sections);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*db.collection("notifications")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(!task.isSuccessful()) {
                            Log.d(TAG, "onComplete: " + task.getException());
                            return;
                        }

                        List<DocumentSnapshot> docs = task.getResult().getDocuments();
                        List<Notification> dataset = new ArrayList<>();
                        HashMap<String, Integer> uniq = new HashMap<>();
                        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

                        for(int i = 0; i < docs.size(); i++) {
                            DocumentSnapshot doc = docs.get(i);
                            Notification notification = new Notification(doc.getString("title"), doc.getDate("date"));
                            dataset.add(notification);

                            if(!uniq.containsKey(notification.getDateString())) {
                                uniq.put(notification.getDateString(), i);
                            }
                        }

                        for(String key: uniq.keySet()) {
                            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(uniq.get(key), key));
                        }

                        updateData(dataset, sections);
                    }
                });*/
    }

    private void updateData(List<Notification> dataset, List<SimpleSectionedRecyclerViewAdapter.Section> sections) {
        this.dataset.clear();
        this.dataset.addAll(dataset);

        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        sectionedAdapter.setSections(sections.toArray(dummy));
        sectionedAdapter.notifyDataSetChanged();
    }
}
