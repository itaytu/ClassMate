package com.example.classmate.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.classmate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class teacher_homePage extends AppCompatActivity {
    private FloatingActionButton button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        button= findViewById(R.id.addClass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Teacher.class);
                startActivity(intent);
            }
        });
    }
}
