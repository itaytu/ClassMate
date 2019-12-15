package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText mFullName, mEmail, mPassword, mPhone;
    private Button mRegisterButton;
    private RadioGroup userRadioGroup;
    private RadioButton userRadioButton;
    private TextView mLoginButton;
    private  String userId;
    private boolean eflag, pflag;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private ProgressBar progressBar;
    boolean isTeacher = false;
    boolean isStudent = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName_register_textView);
        mEmail = findViewById(R.id.email_register_textView);
        mPassword = findViewById(R.id.password_register_textView);
        mPhone = findViewById(R.id.phone_textView);
        mRegisterButton = findViewById(R.id.register_button);
        mLoginButton = findViewById(R.id.go_to_login_button);
        userRadioGroup = findViewById(R.id.userType_radioGroup);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);
        InitListeners();
        register();
    }

    protected void InitListeners() {
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidEmail(s)){
                    mEmail.setError("Invalid Email");
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                    ViewCompat.setBackgroundTintList(mEmail, colorStateList);
                    eflag = false;
                }
                else {
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                    ViewCompat.setBackgroundTintList(mEmail, colorStateList);
                    eflag = true;
                }
            }
        });

        //Password
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidPassword(s)){
                    mPassword.setError("Please enter a password at least 8 characters long");
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                    ViewCompat.setBackgroundTintList(mPassword, colorStateList);
                    pflag = false;
                }
                else {
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                    ViewCompat.setBackgroundTintList(mPassword, colorStateList);
                    pflag = true;
                }
            }
        });
    }


    protected void register() {
        progressBar.setVisibility(View.VISIBLE);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();


                if(userRadioButton.getText().equals("Teacher")) {
                    isTeacher = true;
                }
                else {
                    isStudent = true;
                }
                if(pflag && eflag){
                    Log.d("REGISTER","IN BOOLEAN TRUE");
                    progressBar.setVisibility(View.VISIBLE);
//                    final int finalIsTeacher = isTeacher ? 1 : 0;
//                    final int finalIsStudent = isStudent ? 1 : 0;
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d("REGISTER", "Task Completed");
                                Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                userId=fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userId);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Full-Name", fullName);
                                user.put("Email", email);
                                user.put("Phone", phone);
                                user.put("isTeacher", isTeacher);
                                user.put("isStudent", isStudent);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("REGISTER", "User profile is created" + userId);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("REGISTER", "on failure" + e.toString());

                                    }
                                });
                                Intent intent;
                                if (isStudent) {
                                     intent = new Intent(getApplicationContext(), Skills.class);
                                }else
                                {
                                    intent = new Intent(getApplicationContext(),Teacher.class);
                                }
                                startActivity(intent);

                            }
                            else {
                                Log.d("REGISTER", "onComplete: Failed=" + task.getException().getMessage());
                            }
                        }
                    });
                }
                else {
                    mRegisterButton.setError("Please check your Email and Password");
                }
            }
        });
    }



    private final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private final static boolean isValidPassword(CharSequence target) {
        if(target == null || target.length() < 8)
            return false;

        return true;
    }

    public void backTOlogin(View v){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
    public void checkButton(View view) {
        int radioID = userRadioGroup.getCheckedRadioButtonId();
        userRadioButton = findViewById(radioID);

    }
}
