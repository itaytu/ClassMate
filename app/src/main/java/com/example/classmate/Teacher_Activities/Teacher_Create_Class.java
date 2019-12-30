package com.example.classmate.Teacher_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Models.Classroom;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

//TODO: move logic from adapter to different class

public class Teacher_Create_Class extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ListView listView;

    private ArrayList<Student> allStudents;
    private ArrayList<Student> classStudents;
    private Button submit;
    private TextView classroom_textView;
    private String uuid;
    private List<String> classes;
    private List<String> classFromDb;
    private List<Pair<Student,String>> studentUuid;
    private Teacher_Adapter teacher_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uuid = firebaseAuth.getUid();
        studentUuid = new ArrayList<>();
        submit = findViewById(R.id.submit);
        classroom_textView = findViewById(R.id.classroom_textView);

        classes = new ArrayList<>();
        classStudents = new ArrayList<>();

        Log.d("Teacher Create class","reach here before");
        final DocumentReference documentReferenceTwo = firestore.collection("teachers").document(uuid);
        documentReferenceTwo.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot == null)
                    return;

                classFromDb = (List<String>) documentSnapshot.get("classes");
            }
        });
        // Show all student that register to app.
        firestore.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    allStudents = new ArrayList<>();

                    String fullName;
                    String email;
                    String phone;
                    List<String> skillsList;
                    List<String> improveList;
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        phone=documentSnapshot.getString("phone");
                        fullName=documentSnapshot.getString("fullName");
                        email=documentSnapshot.getString("email");
                        skillsList = (List<String>) documentSnapshot.get("skills");
                        improveList = (List<String>) documentSnapshot.get("weaknesses");
                        Student student = new Student(fullName,email,phone);
                        student.getSkills().addAll(skillsList);
                        student.getWeaknesses().addAll(improveList);
                        Pair<Student,String> pair =new Pair<>(student,documentSnapshot.getId());
                        studentUuid.add(pair);
                        allStudents.add(student);
                    }

                    listView = findViewById(R.id.listView);
                    teacher_adapter = new Teacher_Adapter(Teacher_Create_Class.this, allStudents);
                    listView.setAdapter(teacher_adapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            teacher_adapter.setSelectedIndex(position);
/*                           Teacher_Adapter.setSelectedIndex(position);
                            Teacher_Adapter.setItemColor(isClicked);*/
                            teacher_adapter.notifyDataSetChanged();

                            Student student = teacher_adapter.getItem(position);
                            if(teacher_adapter.isClicked[position]) {
                                if (!classStudents.contains(student)) {
                                    classStudents.add(student);
                                }
                            }
                            else {
                                if (classStudents.contains(student))
                                    classStudents.remove(student);
                            }
                        }
                    });
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DocumentReference documentReference = firestore.collection("classes").document();
                final Classroom classroom = new Classroom(classroom_textView.getText().toString());
                classroom.setUuid(documentReference.getId());
                classroom.setTeacher_uuid(uuid);
                for (Student student : classStudents) {
                    student.setClassroom(classroom.getUuid());
                    classroom.addStudents(student);
                }

                documentReference.set(classroom).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        documentReference.update("uuid",documentReference.getId());
                        classroom.setUuid(documentReference.getId());
                        Toast.makeText(Teacher_Create_Class.this, "class created Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CLASS_CREATION", "Failed to create Classroom: " + e.toString());
                    }
                });

                for (int i = 0;i<studentUuid.size();i++){
                    if (!allStudents.contains(studentUuid.get(i).first)){
                        studentUuid.remove(i);
                        i--;
                    }
                }
                classes.add(classroom.getUuid());
                Log.d("TEACHER C ",classes.toString());
                Log.d("TEACHER C ",classFromDb.toString());
                Log.d("TEACHER C ", studentUuid.size()+"");
                for (Pair<Student,String> pair :studentUuid){
                    DocumentReference reference =firestore.collection("students").document(pair.second);
                    reference.update("classroom",classroom.getUuid());
                }
                classFromDb.addAll(classes);
                documentReferenceTwo.update("classes", classFromDb);
                Intent intent = new Intent(getApplicationContext(), Teacher_HomePage.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Teacher_HomePage.class);
        startActivity(intent);

    }

}

