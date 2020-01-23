package com.example.classmate.Student_Activities.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.classmate.Models.Lesson;
import com.example.classmate.Models.Request;
import com.example.classmate.R;
import com.example.classmate.Student_Activities.Requests_adapter;
import com.example.classmate.Student_Activities.Student_HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Objects;

import javax.annotation.Nullable;

public class requests extends Fragment {
    private ListView listView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private List<String> myRequests;
    private List<String> requests_student_uuid;
    private String uuid;
    private String fullName;
    private Date date;
    private String subject = "";
    private String requesting_student ="";
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

        date = new Date();
        myRequests = new ArrayList<>();
        requests_student_uuid = new ArrayList<>();
        showData();
        return v;
    }



    private void showDialog(FragmentActivity activity, final Request request, final String requestUuid, final String requests_student_uuid, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_requests);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView fullName = dialog.findViewById(R.id.fullName);
        TextView subject  = dialog.findViewById(R.id.subject);
        TextView date = dialog.findViewById(R.id.date);
        fullName.setText("Full-Name : "+ request.getRequesting_student());
        subject.setText("Subject : "+ request.getLesson_subject());
        date.setText("Date : "+request.getLesson_date().toString());
        Log.d("req_uuid", "showDialog: "+requestUuid);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    finish();
                    dialog.dismiss();
                }
                return true;
            }
        });
        Button accept = dialog.findViewById(R.id.accept_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("requests").document(requestUuid)
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Delete requests", "onComplete: requests delete successful");
                        //need to add toast
                        requests_adapter.getRequests().remove(position);
                        requests_adapter.notifyDataSetChanged();
                        DocumentReference reference = firestore.collection("students").document(uuid);
                        reference.update("myRequests",FieldValue.arrayRemove(requestUuid));
                        DocumentReference documentReference = firestore.collection("lessons").document();
                        Lesson lesson = new Lesson(request.getResponding_student(),request.getRequesting_student(),request.getLesson_subject(),request.getLesson_date());
                        documentReference.set(lesson);
                        //update the first student
                        reference.update("myLessons",FieldValue.arrayUnion(documentReference.getId()));
                        //update the second student
                        firestore.collection("students").document(requests_student_uuid).update("myLessons",FieldValue.arrayUnion(documentReference.getId()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("myLessons", "onSuccess: ");
                                    }
                                });
                        ((Student_HomePage) Objects.requireNonNull(getActivity())).setCurrentItem(2);
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

        Button declined = dialog.findViewById(R.id.decline_button);
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("requests").document(requestUuid)
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Delete requests", "onComplete: requests delete successful");
                        requests_adapter.getRequests().remove(position);
                        requests_adapter.notifyDataSetChanged();
                        DocumentReference reference = firestore.collection("students").document(uuid);
                        reference.update("myRequests",FieldValue.arrayRemove(requestUuid));
                        ((Student_HomePage) Objects.requireNonNull(getActivity())).setCurrentItem(2);
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
        Button cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void showData() {
        final DocumentReference documentReference = firestore.collection("students").document(uuid);
        documentReference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null){
                    requests = new ArrayList<>();
                    requests_adapter = new Requests_adapter(Objects.requireNonNull(getActivity()), requests);
                    listView.setAdapter(requests_adapter);
                    myRequests = (List<String>) documentSnapshot.get("myRequests");
                    Log.d("req", "onEvent: " + myRequests);
                    fullName = documentSnapshot.getString("fullName");

                    for (int i = 0; i < myRequests.size(); i++) {
                        DocumentReference reference = firestore.collection("requests").document(myRequests.get(i));
                        reference.addSnapshotListener(Objects.requireNonNull(getActivity()), new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                if (documentSnapshot != null) {
                                    date = (Date) documentSnapshot.get("lesson_date");
                                    subject = documentSnapshot.getString("lesson_subject");
                                    requesting_student = documentSnapshot.getString("requesting_student");
                                    requests_student_uuid.add(requesting_student);

                                    if (requesting_student != null) {
                                        DocumentReference documentReferenceResponding = firestore.collection("users").document(requesting_student);
                                        documentReferenceResponding.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                if(documentSnapshot!=null) {
                                                    String name = documentSnapshot.getString("fullName");
                                                    Request request = new Request(name, fullName, subject, date);
                                                    if(!requests.contains(request)) {
                                                        requests.add(request);
                                                    }
                                                    Log.d("req", "onEvent: " + requests);
                                                    requests_adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(requests.isEmpty() || myRequests.isEmpty() || requests_student_uuid.isEmpty()) {
                    return;
                }
                showDialog(getActivity(),requests.get(position),myRequests.get(position),requests_student_uuid.get(position), position);
            }
        });
    }



}
