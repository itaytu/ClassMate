package com.example.classmate.Student_Activities.Fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Models.Lesson;
import com.example.classmate.Models.Request;
import com.example.classmate.R;
import com.example.classmate.Student_Activities.Requests_adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
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
    private  Requests_adapter requests_adapter;

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
                Log.d("req", "onEvent: "+myRequests);
                fullName = documentSnapshot.getString("fullName");

                for (int i = 0 ; i<myRequests.size();i++){
                    DocumentReference reference = firestore.collection("requests").document(myRequests.get(i));
                    reference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            final Date date = (Date) documentSnapshot.get("lesson_date");
                            final String subject = documentSnapshot.getString("lesson_subject");
                            String requesting_student = documentSnapshot.getString("requesting_student");
                            Log.d("req", "onEvent: " + requesting_student);
                            if (requesting_student!=null){
                                DocumentReference documentReferenceResponding = firestore.collection("users").document(requesting_student);
                                documentReferenceResponding.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                        String phone = documentSnapshot.getString("phone");
                                        String name= documentSnapshot.getString("fullName");
                                        String email = documentSnapshot.getString("email");
                                        Request request = new Request(name,fullName,subject,date);
                                        if (!requests.contains(request))
                                                requests.add(request);
                                        Log.d("req", "onEvent: "+requests);
                                        requests_adapter = new Requests_adapter(getActivity(),requests);
                                        listView.setAdapter(requests_adapter);
                                    }
                                });
                            }

                        }
                    });
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(getActivity(),requests.get(position),myRequests.get(position));
            }
        });

        return v;
    }

    private void showDialog(FragmentActivity activity, final Request request, final String request_uuid) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_requests);
        TextView fullname = dialog.findViewById(R.id.fullName);
        TextView subject  = dialog.findViewById(R.id.subject);
        TextView date = dialog.findViewById(R.id.date);
        fullname.append("Full-Name : "+ request.getRequesting_student());
        subject.append("Subject : "+ request.getLesson_subject());
        date.append("Date : "+request.getLesson_date().toString());

        Button accept = dialog.findViewById(R.id.accapt);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("requests").document(request_uuid)
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Delete requests", "onComplete: requests delete successful");
                                //need to add toast
                                DocumentReference reference = firestore.collection("students").document(uuid);
                                reference.update("myRequests",FieldValue.arrayRemove(request_uuid));
                                DocumentReference documentReference = firestore.collection("lessons").document();
                                Lesson lesson = new Lesson(request.getResponding_student(),request.getRequesting_student(),request.getLesson_subject(),request.getLesson_date());
                                documentReference.set(lesson);
                                reference.update("myLessons",FieldValue.arrayUnion(documentReference.getId()));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Delete requests fail", "onFailure: "+e.toString());
                    }
                });
                dialog.dismiss();
            }
        });

        Button declined = dialog.findViewById(R.id.declined);
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
