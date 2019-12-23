package com.example.classmate.Teacher_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.classmate.Connecting.Login;
import com.example.classmate.MyExpandableListAdapter;
import com.example.classmate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Teacher_HomePage extends AppCompatActivity {
    private List<String> teacherClass=new ArrayList<>();
    private FloatingActionButton button ;
    private ExpandableListView expandableListView;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private Button signOut;
    private String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        signOut = findViewById(R.id.signOut);
        button= findViewById(R.id.addClass);
        expandableListView = findViewById(R.id.expanable_list_view);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        uuid=firebaseAuth.getUid();
        Log.d("HomePage",uuid);
        //TODO need to create map <name class ,students> and show in expandable list view.
        final DocumentReference documentReference = firestore.collection("teachers").document(uuid);
        documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("Test", "onEvent: " + documentSnapshot.toString());
                teacherClass = (List<String>) documentSnapshot.get("classes");
                for (int i = 0 ;i< teacherClass.size();i++){
                    DocumentReference documentReference1 = firestore.collection("classes").document(teacherClass.get(i));
                    documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            Log.d("teacher class", "onEvent: "+documentSnapshot.getString("class_name"));
                        }
                    });
                }
            }
        });


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
