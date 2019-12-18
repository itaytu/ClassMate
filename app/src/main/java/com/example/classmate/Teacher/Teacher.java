package com.example.classmate.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Connecting.Login;
import com.example.classmate.R;
import com.example.classmate.Student.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: create teacher object
//TODO: move logic from adapter to different class

public class Teacher extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ListView listView;
    private List<Student> classStudents;
    private Button submit;
    private TextView classroom;
    private String uuid;
    private List<String> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        classStudents = new ArrayList<>();
        submit = findViewById(R.id.submit);
        classroom = findViewById(R.id.classroom_textView);
        uuid = firebaseAuth.getUid();
        classes = new ArrayList<>();


        // Show all student that register to app.
        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            final ArrayList<Student> studentArrayList=new ArrayList<>();
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

                        Student student = new Student(documentSnapshot.getId(), fullName, email, phone, skills, improve);
                        studentArrayList.add(student);
                    }
                    else if(documentSnapshot.getId().equals(uuid)) {
                        classes = (List<String>) documentSnapshot.get("classes");
                    }

                }
                listView = findViewById(R.id.listView);
                final Teacher_adapter teacher_adapter = new Teacher_adapter(Teacher.this, studentArrayList);
                listView.setAdapter(teacher_adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        teacher_adapter.setSelectedIndex(position);
                        teacher_adapter.notifyDataSetChanged();

                        Student student = teacher_adapter.getItem(position);
                        if(student.isClicked())
                            if(!classStudents.contains(student)){
                                classStudents.add(student);
                            }
                        else
                            classStudents.remove(student);
                    }
                });
            }else{
                Log.d("Teacher class","Error",task.getException());
            }
        }
    });

    submit.setOnClickListener(new View.OnClickListener() {

        //TODO: create random uid per classroom via randomGenerator
        @Override
        public void onClick(View v) {
            DocumentReference documentReference = firestore.collection("Classes").document(classroom.getText().toString());
            String myClass = documentReference.getId();
            classes.add(myClass);
            Map<String, Student> class_students = new HashMap<>();
            for (Student student : classStudents) {
                class_students.put(student.getUuid(), student);
                documentReference.set(class_students).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Teacher.this, "teacher_homePage created Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CLASS_CREATION", "Failed to create classroom: " + e.toString());
                    }
                });
            }

            DocumentReference documentReferenceTwo = firestore.collection("users").document(uuid);
            documentReferenceTwo.update("classes", classes);

            Intent intent = new Intent(getApplicationContext(), teacher_homePage.class);
            startActivity(intent);
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

