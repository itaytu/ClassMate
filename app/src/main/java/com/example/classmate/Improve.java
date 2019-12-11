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
//                Set<String> myImproves = new HashSet<>();
                                              List<String> improveList = new LinkedList<>();
//                                              Map<String, List<String>> myImproves = new HashMap<>();

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
//                                              myImproves.put("improve",improveList);

                                              documentReference.update("improve",improveList);
//                                              documentReference.set(myImproves);
                                              Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                              startActivity(intent);
                                          }
                                      }
        );
    }
}