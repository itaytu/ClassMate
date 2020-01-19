package com.example.classmate.Student_Activities.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.classmate.Models.Lesson;
import com.example.classmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class lessons extends Fragment {

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    private ListView listView;

    private String uuid;
    private List<Lesson> lessonList;

    public lessons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lessons, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        listView = v.findViewById(R.id.listView);
        lessonList = new ArrayList<>();
        uuid = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("students").document(uuid);
        documentReference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    List<String> lesssons = (List<String>) documentSnapshot.get("myLessons");

                    for (int i = 0; i < lesssons.size(); i++) {

                        DocumentReference documentReferenceLessons = firestore.collection("lessons").document(lesssons.get(i));
                        documentReferenceLessons.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                            String Teacher= documentSnapshot.getString("first_student");
//                            String Student= documentSnapshot.getString();
//                            String subject= documentSnapshot.getString();
//                            String date= documentSnapshot.get();
                            }
                        });
                    }
                }
            }
        });


        return v;
    }

}
