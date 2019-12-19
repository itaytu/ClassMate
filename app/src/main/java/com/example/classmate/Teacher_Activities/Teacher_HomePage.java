package com.example.classmate.Teacher_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.classmate.Connecting.Login;
import com.example.classmate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class Teacher_HomePage extends AppCompatActivity {
    private FloatingActionButton button ;
    private FirebaseAuth firebaseAuth;
    private Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        signOut = findViewById(R.id.signOut);
        button= findViewById(R.id.addClass);
        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Teacher_Create_Class.class);
                startActivity(intent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        firebaseAuth.signOut();
//        Intent intent = new Intent(getApplicationContext(), Login.class);
//        startActivity(intent);
    }
}
