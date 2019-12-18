package com.example.classmate.Teacher_Activities;

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
import com.example.classmate.Models.Classroom;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
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
import java.util.List;
import java.util.Objects;

//TODO: create teacher object
//TODO: move logic from adapter to different class

public class Teacher_Create_Class extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ListView listView;

    private ArrayList<Student> classStudents;
    private Button submit;
    private TextView classroom_textView;
    private String uuid;
    private List<String> classes;

    private static boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        uuid = firebaseAuth.getUid();

        submit = findViewById(R.id.submit);
        classroom_textView = findViewById(R.id.classroom_textView);

        classes = new ArrayList<>();
        classStudents = new ArrayList<>();

        // Show all student that register to app.
        firestore.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        Student student = (Student) documentSnapshot.getData();
                        classStudents.add(student);
                    }

                    listView = findViewById(R.id.listView);
                    final Teacher_Adapter teacher_adapter = new Teacher_Adapter(Teacher_Create_Class.this, classStudents);
                    listView.setAdapter(teacher_adapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            teacher_adapter.setSelectedIndex();
/*                           Teacher_Adapter.setSelectedIndex(position);
                            Teacher_Adapter.setItemColor(isClicked);*/
                            teacher_adapter.notifyDataSetChanged();

                            Student student = teacher_adapter.getItem(position);
                            if(isClicked)
                                if(!classStudents.contains(student)){
                                    classStudents.add(student);
                                }
                                else
                                    classStudents.remove(student);
                        }
                    });
                }
            }
        });





/*        firestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            final ArrayList<Student_v1> studentV1ArrayList =new ArrayList<>();
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

                        Student_v1 studentV1 = new Student_v1(documentSnapshot.getId(), fullName, email, phone, skills, improve);
                        studentV1ArrayList.add(studentV1);
                    }
                    else if(documentSnapshot.getId().equals(uuid)) {
                        classes = (List<String>) documentSnapshot.get("classes");
                    }

                }
                listView = findViewById(R.id.listView);
                final Teacher_Adapter Teacher_Adapter = new Teacher_Adapter(Teacher_Create_Class.this, studentV1ArrayList);
                listView.setAdapter(Teacher_Adapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Teacher_Adapter.setSelectedIndex(position);
                        Teacher_Adapter.notifyDataSetChanged();

                        Student_v1 studentV1 = Teacher_Adapter.getItem(position);
                        if(studentV1.isClicked())
                            if(!classStudentV1s.contains(studentV1)){
                                classStudentV1s.add(studentV1);
                            }
                        else
                            classStudentV1s.remove(studentV1);
                    }
                });
            }else{
                Log.d("Teacher_Create_Class","Error: ", task.getException());
            }
        }
    });*/

        submit.setOnClickListener(new View.OnClickListener() {

            //TODO: create random uid per Classroom via randomGenerator
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = firestore.collection("classes").document();
                Classroom classroom = new Classroom(classroom_textView.getText().toString());
                classroom.setUuid(documentReference.toString());
                classroom.setTeacher_uuid(uuid);
                classes.add(classroom.getClass_name());
                for (Student student : classStudents) {
                    student.setClassroom(classroom.getUuid());
                    classroom.addStudents(student);
                }

                documentReference.set(classroom).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Teacher_Create_Class.this, "class created Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("CLASS_CREATION", "Failed to create Classroom: " + e.toString());
                    }
                });


                /*Map<String, Student_v1> class_students = new HashMap<>();
                for (Student student : classStudents) {
                    class_students.put(student.getUuid(), student);
                    documentReference.set(class_students).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Teacher_Create_Class.this, "Teacher_HomePage created Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("CLASS_CREATION", "Failed to create Classroom: " + e.toString());
                        }
                    });
                }*/

                DocumentReference documentReferenceTwo = firestore.collection("teachers").document(uuid);
                documentReferenceTwo.update("classes", classes);

                Intent intent = new Intent(getApplicationContext(), Teacher_HomePage.class);
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

    public static void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public static boolean getClicked() { return isClicked; }
}

