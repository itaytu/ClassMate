package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Student> studentArrayList=new ArrayList<>();
                String fullName ;
                String email;
                String phone;
                List<String> skillsList=new ArrayList<>();
                List<String> improveList=new ArrayList<>();
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        if(documentSnapshot.getBoolean("isStudent").booleanValue()) {
                            String skills="";
                            String improve="";
                            fullName = documentSnapshot.getString("Full-Name");
                            email = documentSnapshot.getString("Email");
                            phone = documentSnapshot.getString("Phone");
                            skillsList = (List<String>) documentSnapshot.get("skills");
                            improveList = (List<String>) documentSnapshot.get("improve");
                            for(String s :skillsList){
                                if (skills.isEmpty()){
                                    skills = skills+s;
                                }else
                                    skills = skills+ " ," +s ;

                            }
                            for (String s:improveList){
                                if (improve.isEmpty()){
                                    improve = improve+s;
                                }else
                                    improve = improve+ " ," +s ;
                            }

                            Student student = new Student(fullName, email, phone,skills,improve);
//                            Student student = new Student(fullName, email, phone);
                            studentArrayList.add(student);
                        }
                    }
                    listView = findViewById(R.id.listView);
                    Teacher_adapter teacher_adapter = new Teacher_adapter(Teacher.this,studentArrayList);
                    listView.setAdapter(teacher_adapter);
                }else{
                    Log.d("Teacher class","Error",task.getException());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
//        firebaseAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }
}
