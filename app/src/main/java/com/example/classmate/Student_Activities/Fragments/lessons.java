package com.example.classmate.Student_Activities.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.classmate.Models.Lesson;
import com.example.classmate.R;
import com.example.classmate.Student_Activities.Lessons_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private Lessons_adapter lessons_adapter;

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
        listView = v.findViewById(R.id.listview);
        lessonList = new ArrayList<>();
        uuid = firebaseAuth.getCurrentUser().getUid();



        DocumentReference documentReference = firestore.collection("students").document(uuid);
        documentReference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    List<String> lessons = (List<String>) documentSnapshot.get("myLessons");
                    Log.d("lesssons", "onEvent: "+lessons);
                    lessonList.clear();
                    lessons_adapter = new Lessons_adapter(getActivity(), (ArrayList<Lesson>) lessonList);
                    listView.setAdapter(lessons_adapter);
                    for (int i = 0; i < lessons.size(); i++) {
                        DocumentReference documentReferenceLessons = firestore.collection("lessons").document(lessons.get(i));
                        documentReferenceLessons.addSnapshotListener(Objects.requireNonNull(getActivity()), new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                //TODO CHECK IF THE DATE OF LESSON IS EXPIRED
                                if (documentSnapshot !=null) {
                                    String Teacher = documentSnapshot.getString("teacher");
                                    String Student = documentSnapshot.getString("second_student");
                                    String subject = documentSnapshot.getString("subject");
                                    Date date = (Date) documentSnapshot.get("lesson_date");
                                    Lesson lesson = new Lesson(Teacher, Student, subject, date);
                                    if (!lessonList.contains(lesson)) {
                                        lessonList.add(lesson);
                                    }
                                    Log.d("Lessons_adapter", "onEvent: " + lessonList);
                                    lessons_adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }

                }

            }
        });

        return v;
    }

}
