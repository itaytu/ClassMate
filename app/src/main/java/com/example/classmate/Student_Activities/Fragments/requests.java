package com.example.classmate.Student_Activities.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.classmate.Models.Request;
import com.example.classmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class requests extends Fragment {
    private ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private List<String> myRequests;
    private String uuid;
    private String fullName;
    private ArrayList<Request> requests;

    public requests() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_request_lesson, container,false);
        listView = v.findViewById(R.id.listView);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uuid  = firebaseAuth.getUid();
        requests = new ArrayList<>();

        final DocumentReference documentReference = firestore.collection("students").document(uuid);
        documentReference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                myRequests = (List<String>) documentSnapshot.get("myRequests");
                fullName = documentSnapshot.getString("fullName");

                for (int i = 0 ; i<myRequests.size();i++){
                    DocumentReference reference = firestore.collection("requests").document(myRequests.get(i));
                    reference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            Date date = (Date) documentSnapshot.get("lesson_date");
                            String subject = documentSnapshot.getString("lesson_subject");
                            String requesting_student = documentSnapshot.getString("requesting_student");
                            DocumentReference documentReferenceResponding = firestore.collection("users").document(requesting_student);
                            documentReferenceResponding.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    String phone = documentSnapshot.getString("phone");
                                    String name= documentSnapshot.getString("fullName");
                                    String email = documentSnapshot.getString("email");

                                    
                                }
                            });
                        }
                    });

                }
            }
        });


        return v;
    }

}
