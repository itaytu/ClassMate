package com.example.classmate.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class HomePage extends AppCompatActivity {

    private TextView fullName , email ,phone,skills,improves;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String useId;
    private List<String> skillsList = new ArrayList<>();
    private List<String> improvesList = new ArrayList<>();
    private String stringImproves="";
    private String stringSkills="";
    private Button logout , findMatch;

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
        logout= findViewById(R.id.logout_button);

        findMatch = findViewById(R.id.findMatch);

        useId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(useId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("PROFILE", "here");
                phone.append(documentSnapshot.getString("Phone"));
                fullName.append(documentSnapshot.getString("Full-Name"));
                email.append(documentSnapshot.getString("Email"));
                skillsList = (List<String>) documentSnapshot.get("skills");
                improvesList = (List<String>) documentSnapshot.get("improve");
                for(String s :skillsList){
                    if (stringSkills.isEmpty()){
                        stringSkills = stringSkills+s;
                    }else
                    stringSkills = stringSkills+ " ," +s ;

                }
                for (String s:improvesList){
                    if (stringImproves.isEmpty()){
                        stringImproves = stringImproves+s;
                    }else
                    stringImproves = stringImproves+ " ," +s ;
                }
                skills.append(stringSkills);
                improves.append(stringImproves);
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
                Intent intent = new Intent(getApplicationContext(), Algorithm.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed() {
        fAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

}