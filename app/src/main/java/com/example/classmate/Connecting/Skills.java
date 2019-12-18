package com.example.classmate.Connecting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.classmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

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
        sendButton.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        userId = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userId);
                        List<String> skillsList = new LinkedList<>();

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
                            Intent intent = new Intent(getApplicationContext(), Improve.class);
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