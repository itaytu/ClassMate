package com.example.classmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

public class Improve extends AppCompatActivity {

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
        setContentView(R.layout.activity_improve);

        english = findViewById(R.id.english);
        french =  findViewById(R.id.french);
        hebrew =  findViewById(R.id.hebrew);
        spanish = findViewById(R.id.spanish);
        arabic =  findViewById(R.id.arabic);
        physics = findViewById(R.id.physics);
        math =  findViewById(R.id.math);
        chemistry =findViewById(R.id.chemistry);
        programming =  findViewById(R.id.programming);
        biology =  findViewById(R.id.biology);

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
                        List<String> improveList = new LinkedList<>();

                        if(english.isChecked())
                            improveList.add("english");
                        if(french.isChecked())
                            improveList.add("french");
                        if(hebrew.isChecked())
                            improveList.add("hebrew");
                        if(spanish.isChecked())
                            improveList.add("spanish");
                        if(arabic.isChecked())
                            improveList.add("arabic");
                        if(physics.isChecked())
                            improveList.add("physics");
                        if(math.isChecked())
                            improveList.add("math");
                        if(chemistry.isChecked())
                            improveList.add("chemistry");
                        if(programming.isChecked())
                            improveList.add("programming");
                        if(biology.isChecked())
                            improveList.add("biology");

                        if(!improveList.isEmpty()) {
                            documentReference.update("improve", improveList);
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                        }
                        else {
                            sendButton.setError("Please choose at least one improvement");
                        }

                    }
                });
    }
}