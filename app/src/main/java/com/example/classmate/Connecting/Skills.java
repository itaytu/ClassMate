package com.example.classmate.Connecting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

public class Skills extends AppCompatActivity{

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

    private ArrayList<String> skillsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        english = findViewById(R.id.english);
        french = findViewById(R.id.french);
        hebrew = findViewById(R.id.hebrew);
        spanish = findViewById(R.id.spanish);
        arabic = findViewById(R.id.arabic);
        physics = findViewById(R.id.physics);
        math = findViewById(R.id.math);
        chemistry = findViewById(R.id.chemistry);
        programming = findViewById(R.id.programming);
        biology = findViewById(R.id.biology);

        fAuth = FirebaseAuth.getInstance();
        sendButton=findViewById(R.id.send);
        fStore=FirebaseFirestore.getInstance();

        send();
    }



    protected void send() {
        sendButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        userId = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("students").document(userId);
                        skillsList = new ArrayList<>();
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

                        if(!skillsList.isEmpty()) {
                            documentReference.update("skills", skillsList);
                            Log.d("Skills","go to next activity");
                            Intent intent = new Intent(getApplicationContext(), Weaknesses.class);
                            intent.putStringArrayListExtra("skillsList", skillsList);
                            startActivity(intent);
                        }

                        else{
                            sendButton.setError("Please choose at least one skill");
                        }
                    }
                }
        );
    }
}