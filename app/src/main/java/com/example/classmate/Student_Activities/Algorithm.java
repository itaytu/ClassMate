package com.example.classmate.Student_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.classmate.Models.Student;
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
    private List<String> myWeaknesses = new ArrayList<>();
    private List<String> mySkills = new ArrayList<>();
    private String myClass;
    private String myEmail;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        listView = findViewById(R.id.listView);
        final DocumentReference documentReference= firestore.collection("students").document(userId);
        documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                myWeaknesses = (List<String>) documentSnapshot.get("weaknesses");
                myClass=documentSnapshot.getString("classroom");
                myEmail = documentSnapshot.getString("email");
                mySkills = (List<String>) documentSnapshot.get("skills");
                firestore.collection("classes").document(myClass).addSnapshotListener(Algorithm.this,new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        ArrayList<Student> students = new ArrayList<>();
                        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) documentSnapshot.get("studentsList");
                        for (HashMap<String, Object> map:list){
                            String studentEmail = (String) map.get("email");
                            if(!studentEmail.equals(myEmail)){
                                List<String> skills= (List<String>) map.get("skills");
                                List<String> Weaknesses= (List<String>) map.get("weaknesses");
                                List<String> tempMyWeaknesses = new ArrayList<>(myWeaknesses);
                                List<String> tempMySkills = new ArrayList<>(mySkills);
                                tempMyWeaknesses.retainAll(skills);
                                tempMySkills.retainAll(Weaknesses);
                                if (!tempMyWeaknesses.isEmpty()){
                                    String name = (String) map.get("fullName");
                                    String email = (String) map.get("email");
                                    String phone = (String) map.get("phone");
                                    Student student = new Student(name,email,phone);
                                    student.getSkills().addAll(tempMyWeaknesses);
                                    if (tempMySkills != null) {
                                        student.getWeaknesses().addAll(tempMySkills);
                                    }
                                    students.add(student);
                                }
                            }
                        }
                        Algorithm_adapter algorithm_adapter = new Algorithm_adapter(Algorithm.this,students);
                        listView.setAdapter(algorithm_adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // TODO move to set lesson activity
                            }
                        });
                    }
                });
            }

        });
    }
}
