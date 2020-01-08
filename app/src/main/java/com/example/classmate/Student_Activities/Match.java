package com.example.classmate.Student_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.classmate.Models.Lesson;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import javax.annotation.Nullable;

public class Match extends AppCompatActivity {
    private TextView first_student, second_student;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String useId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        final Student s = (Student) getIntent().getSerializableExtra("st");
        first_student = findViewById(R.id.first_student);
        second_student = findViewById(R.id.second_student);
        useId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("students").document(useId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                first_student.append(documentSnapshot.getString("fullName"));
                second_student.append(s.getFullName());
            }

        });


        final Lesson lesson = new Lesson(useId, s.getUserID());
        DocumentReference documentReferenceUsers = fStore.collection("lessons").document();
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

        DocumentReference docFirst = fStore.collection("students").document(useId);
        DocumentReference docSec = fStore.collection("students").document(s.getUserID());
        docFirst.update("myLessons",documentReferenceUsers.getId());
        docSec.update("myLessons", documentReferenceUsers.getId());


    }
}
