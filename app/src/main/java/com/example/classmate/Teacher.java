package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        // Show all student that register to app.
        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            ArrayList<Student> studentArrayList=new ArrayList<>();
            String fullName;
            String email;
            String phone;
            List<String> skillsList;
            List<String> improveList;
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    if(documentSnapshot.getBoolean("isStudent")) {
                        String skills="";
                        String improve="";
                        fullName = documentSnapshot.getString("Full-Name");
                        email = documentSnapshot.getString("Email");
                        phone = documentSnapshot.getString("Phone");
                        skillsList = (List<String>) documentSnapshot.get("skills");
                        improveList = (List<String>) documentSnapshot.get("improve");
                        if(skillsList != null) {
                            for (String s : skillsList) {
                                if (skills.isEmpty()) {
                                    skills = skills + s;
                                } else
                                    skills = skills + " ," + s;

                            }
                        }
                        if(improveList != null) {
                            for (String s : improveList) {
                                if (improve.isEmpty()) {
                                    improve = improve + s;
                                } else
                                    improve = improve + " ," + s;
                            }
                        }
                        Student student = new Student(fullName, email, phone,skills,improve);
                        studentArrayList.add(student);
                    }
                }
                listView = findViewById(R.id.listView);
                Teacher_adapter teacher_adapter = new Teacher_adapter(Teacher.this,studentArrayList);
                listView.setAdapter(teacher_adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(Teacher.this,"position : "+ position,Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Log.d("Teacher class","Error",task.getException());
            }
        }
    });

}

    @Override
    public void onBackPressed() {
        firebaseAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }
}

