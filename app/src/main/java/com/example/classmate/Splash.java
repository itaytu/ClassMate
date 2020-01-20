package com.example.classmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.classmate.Connecting.Login;
import com.example.classmate.Student_Activities.Student_HomePage;
import com.example.classmate.Teacher_Activities.Teacher_HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import javax.annotation.Nullable;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String uuid;
    private Boolean isTeacher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        uuid=firebaseAuth.getUid();
        firestore= FirebaseFirestore.getInstance();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!= null){
                    DocumentReference documentReference =firestore.collection("users").document(uuid);
                    documentReference.addSnapshotListener(Splash.this,new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            isTeacher = Objects.requireNonNull(documentSnapshot).getBoolean("teacher");
                            Intent intent;
                            if (isTeacher){
                                intent = new Intent(getApplicationContext(), Teacher_HomePage.class);
                            }
                            else {
                                intent = new Intent(getApplicationContext(), Student_HomePage.class);
                            }
                            startActivity(intent);
                            Splash.this.finish();

                        }
                    });
                }else{
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
