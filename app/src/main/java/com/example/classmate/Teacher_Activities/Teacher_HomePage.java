package com.example.classmate.Teacher_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.classmate.Connecting.Login;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

public class Teacher_HomePage extends AppCompatActivity {

    private FloatingActionButton addClassButton;
    private Button signOut;

    private ExpandableListView expandableListView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;


    private List<String> teacherClasses = new ArrayList<>();
    private String uuid;
    private HashMap<String, List<Student>> hashMap;
    //TODO add phone clicked
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        signOut = findViewById(R.id.signOut);
        addClassButton = findViewById(R.id.addClass);
        expandableListView = findViewById(R.id.expanable_list_view);
        hashMap = new HashMap<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        uuid=firebaseAuth.getUid();

        final DocumentReference documentReference = firestore.collection("teachers").document(uuid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot == null)
                    return;

                Log.d("Test", "onEvent: " + Objects.requireNonNull(documentSnapshot).toString());
                teacherClasses = (List<String>) documentSnapshot.get("classes");
                Log.d("size", "onEvent: "+teacherClasses.size());
                for (int i = 0; i < Objects.requireNonNull(teacherClasses).size(); i++){
                    CollectionReference collectionReference = firestore.collection("classes");
                    Query query = collectionReference.whereEqualTo("uuid", teacherClasses.get(i));
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : Objects.requireNonNull(task.getResult())) {
                                    String fullName;
                                    String email;
                                    String phone;
                                    List<String> skillsList;
                                    List<String> improveList;
                                    List<HashMap<String, Object>> list= (List<HashMap<String, Object>>) queryDocumentSnapshot.get("studentsList");
                                    assert list != null;
                                    List<Student> students = new ArrayList<>();
                                    for(int i = 0; i<list.size(); i++){
                                        HashMap<String, Object> map= list.get(i);
                                        fullName=(String) map.get("fullName");
                                        email=(String) map.get("email");
                                        phone=(String) map.get("phone");
                                        skillsList = (List<String>) map.get("skills");
                                        improveList = (List<String>) map.get("weaknesses");
                                        Student student = new Student(fullName,email,phone);
                                        student.getSkills().addAll(skillsList);
                                        student.getWeaknesses().addAll(improveList);
                                        students.add(student);
                                    }
                                    hashMap.put(queryDocumentSnapshot.getString("class_name"),students);
                                    MyExpandableListAdapter myExpandableListAdapter = new MyExpandableListAdapter(hashMap);
                                    expandableListView.setAdapter(myExpandableListAdapter);
                                }
                            }
                            else {
                                Log.d("ERROR: ", Objects.requireNonNull(task.getException()).toString());
                            }
                        }
                    });

                }
            }
        });


        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Teacher_Create_Class.class);
                startActivity(intent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        firebaseAuth.signOut();
//        Intent intent = new Intent(getApplicationContext(), Login.class);
//        startActivity(intent);
    }


}
