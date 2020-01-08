package com.example.classmate.Student_Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classmate.Models.Lesson;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Student_Create_Lesson extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String first_student_id;
    private String second_student_id;

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__create__lesson);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        first_student_id = fAuth.getCurrentUser().getUid();

        final Student second_student = (Student) getIntent().getSerializableExtra("st");

        CollectionReference collectionReference = fStore.collection("students");
        final Query query = collectionReference.whereEqualTo("email", Objects.requireNonNull(second_student).getEmail());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())){
                        second_student_id = documentSnapshot.getId();
                        Log.d("SECOND STUDENT ID: " , documentSnapshot.getId());
                    }
                    DocumentReference docFirst = fStore.collection("students").document(first_student_id);
                    DocumentReference docSec = fStore.collection("students").document(second_student_id);

                    final Lesson lesson = new Lesson(first_student_id, second_student_id);
                    final DocumentReference documentReferenceUsers = fStore.collection("lessons").document();

                    documentReferenceUsers.set(lesson).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Lesson_scheduled", "Lesson scheduled successfully ");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Lesson_scheduled", "Lesson creation failed");
                        }
                    });

                    docFirst.update("myLessons", documentReferenceUsers.getId());
                    docSec.update("myLessons", documentReferenceUsers.getId());

                    spinner = findViewById(R.id.lessons_spinner);

                    ArrayAdapter<String> lessons_adapter = new ArrayAdapter<>(Student_Create_Lesson.this, android.R.layout.simple_spinner_item, second_student.getSkills());
                    lessons_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(lessons_adapter);

                }

                else
                    Log.d("ERROR NO STUDENT: ", task.getException().toString());
            }
        });


    }

}
