package com.example.classmate.Student_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.classmate.Models.Lesson;
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

import javax.annotation.Nullable;

public class Match extends AppCompatActivity {
    private static final String TAG = "DocSnippets";
    private TextView first_student, second_student;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String useId;
    private ArrayList<Lesson> first,second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        //////////////////
        first=new ArrayList<>();
        ////////////////

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


/////////////////////changing  .document name///////////////////////////

//        String uuid =fAuth.getCurrentUser().getUid();
        final Lesson lesson = new Lesson(useId, s.getUserID());
//        DocumentReference documentReferenceUsers = fStore.collection("lessons").document(uuid);
//        documentReferenceUsers.set(lesson).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d("Lesson_scheduled", "Lesson scheduled successfully ");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("Lesson_scheduled", "Lesson creation failed");
//            }
//        });
//         ArrayList<Lesson> first;
//        first=new ArrayList<>();


        DocumentReference docRef = fStore.collection("students").document(useId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    first=(ArrayList<Lesson>)document.get("myLessons");
//                    Log.d("bibip",first.toString());
                    first.add(lesson);
                    Log.d("noma", document.get("myLessons").toString());
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });










//        Log.d("didi", first.toString());
//        //////////////updating first...../////////////////
////        DocumentReference docStudents = fStore.collection("students").document(useId);
//        docRef
//                .update("myLessons", first)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully updated!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error updating document", e);
//                    }
//                });







    }
}
