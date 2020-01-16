package com.example.classmate.Student_Activities.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Connecting.Login;
import com.example.classmate.R;
import com.example.classmate.Student_Activities.Algorithm;
import com.example.classmate.Student_Activities.Student_HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

public class home_page extends Fragment {
    private TextView fullName , email , phone, skills, weaknesses;
    private Button logout , findMatch;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String uid;
    private String classroomId;

    private List<String> skillsList = new ArrayList<>();
    private List<String> weaknessesList = new ArrayList<>();

    public home_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student__home__page, container,false);
        fullName = v.findViewById(R.id.profileName);
        email = v.findViewById(R.id.profileEmail);
        phone = v.findViewById(R.id.profilePhoneNumber);
        skills = v.findViewById(R.id.skills);
        weaknesses =v.findViewById(R.id.improves);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        logout= v.findViewById(R.id.logout_button);

        findMatch = v.findViewById(R.id.findMatch);
        uid = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("students").document(uid);
        documentReference.addSnapshotListener(getActivity(),new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Log.d("PROFILE", "here");
                if(documentSnapshot == null)
                    return;
                phone.setText("Phone : "+documentSnapshot.getString("phone"));
                fullName.setText("Full-Name : "+documentSnapshot.getString("fullName"));
                email.setText("Email : "+documentSnapshot.getString("email"));
                classroomId = documentSnapshot.getString("classroom");
                skillsList = (List<String>) documentSnapshot.get("skills");
                weaknessesList = (List<String>) documentSnapshot.get("weaknesses");
                String stringSkills = TextUtils.join(", ", skillsList);
                String stringImproves = TextUtils.join(", ", weaknessesList);
                skills.setText("Skills : "+stringSkills);
                weaknesses.setText("Weaknesses : "+stringImproves);
            }

        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        findMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classroomId.isEmpty())
                    Toast.makeText(getActivity(), "You are not assigned to any class", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getActivity(), Algorithm.class);
                    startActivity(intent);
                }
            }
        });

        return v;

    }

}
