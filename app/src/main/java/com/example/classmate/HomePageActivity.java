package com.example.classmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class HomePageActivity extends AppCompatActivity {

    private TextView fullName , email ,phone;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String useId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page2);

        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhoneNumber);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        useId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(useId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("PROFILE","here");
                phone.setText(documentSnapshot.getString("Phone"));
                fullName.setText(documentSnapshot.getString("Full-Name"));
                email.setText(documentSnapshot.getString("Email"));
            }
        });

    }

//    public void logout(View view) {
//        Intent intent = new Intent(getApplicationContext(), Login.class);
//        startActivity(intent);
//    }
}
