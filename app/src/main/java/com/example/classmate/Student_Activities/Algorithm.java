package com.example.classmate.Student_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.classmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

public class Algorithm extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private String userId;
    private List<String> weaknesses = new ArrayList<>();
    private List<String> otherSkills = new ArrayList<>();
    private String myClass;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

       final DocumentReference documentReference= firestore.collection("students").document(userId);

       documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
               weaknesses = (List<String>) documentSnapshot.get("weaknesses");
               myClass=documentSnapshot.getString("classroom");
           }
           DocumentReference documentReferenceClass = (DocumentReference) firestore.collection("classes").document(myClass).addSnapshotListener(Algorithm.this,new EventListener<DocumentSnapshot>() {
               @Override
               public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                   List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) documentSnapshot.get("studentsList");
                   for (HashMap<String, Object> map:list){

                   }
               }
           });

       });





    }
}
/*
        firestore.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                        if(documentSnapshot == null)
                            return;

                        if (documentSnapshot.getId().equals(userId)){
                            weaknesses = (List<String>) documentSnapshot.get("weaknesses");
                            break;
                        }
                    }

                    ArrayList<Student> students = new ArrayList<>();
                    if(weaknesses != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (documentSnapshot == null)
                                return;

                            if (!documentSnapshot.getId().equals(userId)) {
                                ArrayList<String> skillsToLearn = new ArrayList<>();
                                otherSkills = (List<String>) documentSnapshot.get("skills");
                                if(otherSkills != null) {
                                    for (String weakness : weaknesses) {
                                        if (otherSkills.contains(weakness)) {
                                            skillsToLearn.add(weakness);
                                            break;
                                        }
                                    }
                                    if(!skillsToLearn.isEmpty()) {
                                        Student otherStudent = new Student((Student) documentSnapshot.getData());
                                        for (String skill : skillsToLearn)
                                            otherStudent.addSkills(skill);
                                        students.add(otherStudent);
                                    }
                                }
                            }
                        }
                    }
                    listView= findViewById(R.id.listView);
                    Algorithm_adapter algorithm_adapter = new Algorithm_adapter(Algorithm.this, students);
                    listView.setAdapter(algorithm_adapter);
                }
            }
        });*/