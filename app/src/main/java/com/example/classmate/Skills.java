package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Skills extends AppCompatActivity {

    private Button sendButton;
    private FirebaseFirestore fStore;
    private  String userId;
    private FirebaseAuth fAuth;

    private CheckBox english;
    private CheckBox french;
    private CheckBox hebrew;
    private CheckBox spanish;
    private CheckBox arabic;
    private CheckBox physics;
    private CheckBox math;
    private CheckBox chemistry;
    private CheckBox programming;
    private CheckBox biology;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        english = (CheckBox) findViewById(R.id.english);
        french = (CheckBox) findViewById(R.id.french);
        hebrew = (CheckBox) findViewById(R.id.hebrew);
        spanish = (CheckBox) findViewById(R.id.spanish);
        arabic = (CheckBox) findViewById(R.id.arabic);
        physics = (CheckBox) findViewById(R.id.physics);
        math = (CheckBox) findViewById(R.id.math);
        chemistry = (CheckBox) findViewById(R.id.chemistry);
        programming = (CheckBox) findViewById(R.id.programming);
        biology = (CheckBox) findViewById(R.id.biology);

        fAuth = FirebaseAuth.getInstance();
        sendButton=findViewById(R.id.send);
        fStore=FirebaseFirestore.getInstance();

        send();
    }



    protected void send() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userId);
//                Set<String> mySkill = new HashSet<>();
                List<String> skillsList = new LinkedList<>();
//                Map<String, List<String>> mySkill = new HashMap<>();

                if(english.isChecked())
                    skillsList.add("english");
                if(french.isChecked())
                    skillsList.add("french");
                if(hebrew.isChecked())
                    skillsList.add("hebrew");
                if(spanish.isChecked())
                    skillsList.add("spanish");
                if(arabic.isChecked())
                    skillsList.add("arabic");
                if(physics.isChecked())
                    skillsList.add("physics");
                if(math.isChecked())
                    skillsList.add("math");
                if(chemistry.isChecked())
                    skillsList.add("chemistry");
                if(programming.isChecked())
                    skillsList.add("programming");
                if(biology.isChecked())
                    skillsList.add("biology");
//                mySkill.put("skillsANDdemands",skillsList);
                documentReference.update("skills",skillsList);
//                documentReference.set(mySkill);
                Intent intent = new Intent(getApplicationContext(), Improve.class);
                startActivity(intent);
            }
        }
        );
    }
}