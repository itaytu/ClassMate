package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Algorithm extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String userId;
    private ListView listView;
    private List<String> improveList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

//        DocumentReference documentReference =firestore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                improveList= (List<String>) documentSnapshot.get("improve");
//            }
//        });
        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> otherSkills = new ArrayList<>();
                String improveToLearn="";
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        if (documentSnapshot.getId().equals(userId)){
                            improveList= (List<String>) documentSnapshot.get("improve");
                            break;
                        }
                    }
                    String fullName ;
                    String email;
                    String phone;
                    ArrayList<Student> students = new ArrayList<>();
                    if(improveList != null) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (!documentSnapshot.getId().equals(userId)) {
                                if (documentSnapshot.getBoolean("isStudent")) {
                                    otherSkills = (List<String>) documentSnapshot.get("skills");
                                    if(otherSkills != null) {
                                        for (String s : improveList) {
                                            if (otherSkills.contains(s)) {
                                                improveToLearn = s;
                                                fullName = documentSnapshot.getString("Full-Name");
                                                email = documentSnapshot.getString("Email");
                                                phone = documentSnapshot.getString("Phone");
                                                Student student = new Student(fullName, email, phone, improveToLearn);
                                                students.add(student);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    listView= findViewById(R.id.listView);
                    Algorithm_adapter algorithm_adapter = new Algorithm_adapter(Algorithm.this,students);
                    listView.setAdapter(algorithm_adapter);
                }
            }
        });



    }
}
