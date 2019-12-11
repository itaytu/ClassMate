package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

public class HomePage extends AppCompatActivity {

    private TextView fullName , email ,phone,skills,improves;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String useId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhoneNumber);
        skills = findViewById(R.id.skills);
        improves= findViewById(R.id.improves);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        useId = fAuth.getCurrentUser().getUid();
//////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////
        DocumentReference documentReference = fStore.collection("users").document(useId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("PROFILE", "here");
                phone.setText(documentSnapshot.getString("Phone"));
                fullName.setText(documentSnapshot.getString("Full-Name"));
                email.setText(documentSnapshot.getString("Email"));

                ////////////////////////////////////////
                fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> skillsList = new ArrayList<>();
                            List<String> improvesList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(useId)) {
                                    Log.d("myid", document.getId());
                                    skillsList = (List<String>) document.get("skills");
                                    improvesList = (List<String>) document.get("improve");
                                    break;
                                }
                            }
                            skills.setText(skillsList.toString());
                            improves.setText(improvesList.toString());
                            Log.d("list", skillsList.toString());
                        } else {
                            Log.d("list", "Error getting documents: ", task.getException());
                        }
                    }
                });
                /////////////////////////////////////////////
            }

        });

//        fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    Log.d("bibi", "********************");
//                    List<String> list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        list = (List<String>) document.get("skills");
//                    }
//                    Log.d("bibi", "%%%%%%%%%%%%%%%%%%%%%%%%%");
//                    Log.d("list", list.toString());
//                } else {
//                    Log.d("list", "Error getting documents: ", task.getException());
//                }
//            }
//        });


//    public void logout(View view) {
//        Intent intent = new Intent(getApplicationContext(), Login.class);
//        startActivity(intent);
//    }

    }
}