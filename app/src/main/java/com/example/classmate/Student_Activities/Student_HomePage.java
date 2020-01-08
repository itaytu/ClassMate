package com.example.classmate.Student_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Connecting.Login;
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

public class Student_HomePage extends AppCompatActivity {

    private TextView fullName , email , phone, skills, weaknesses;
    private Button logout , findMatch;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String uid;
    private String classroomId;

    private List<String> skillsList = new ArrayList<>();
    private List<String> weaknessesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhoneNumber);
        skills = findViewById(R.id.skills);
        weaknesses = findViewById(R.id.improves);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        logout= findViewById(R.id.logout_button);

        findMatch = findViewById(R.id.findMatch);
        uid = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("students").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("PROFILE", "here");
                if(documentSnapshot == null)
                    return;
                phone.append(documentSnapshot.getString("phone"));
                fullName.append(documentSnapshot.getString("fullName"));
                email.append(documentSnapshot.getString("email"));
                classroomId = documentSnapshot.getString("classroom");
                skillsList = (List<String>) documentSnapshot.get("skills");
                weaknessesList = (List<String>) documentSnapshot.get("weaknesses");
                String stringSkills = TextUtils.join(", ", skillsList);
                String stringImproves = TextUtils.join(", ", weaknessesList);
                skills.append(stringSkills);
                weaknesses.append(stringImproves);
            }

        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        findMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classroomId.isEmpty())
                    Toast.makeText(Student_HomePage.this, "You are not assigned to any class", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), Algorithm.class);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        fAuth.signOut();
        Intent intent = new Intent(this.getApplicationContext(), Login.class);
        startActivity(intent);
    }

}