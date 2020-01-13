package com.example.classmate.Connecting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmate.Student_Activities.Student_HomePage;
import com.example.classmate.R;
import com.example.classmate.Teacher_Activities.Teacher_HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import javax.annotation.Nullable;

public class Login extends AppCompatActivity {

    private Button loginButton;
    private TextView register;
    private EditText emailEditText , passwordEditText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private static final String TAG = "Login";
    private String userId;

    private boolean pflag = false, eflag = false;
    private Boolean isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        register=findViewById(R.id.register_textView);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        InitListeners();
        Login();
    }

    protected void InitListeners(){
        //Email
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                    if(!isValidEmail(s)){
                        emailEditText.setError("Invalid Email");
                        ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                        ViewCompat.setBackgroundTintList(emailEditText, colorStateList);
                        eflag = false;
                    }
                    else {
                        ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                        ViewCompat.setBackgroundTintList(emailEditText, colorStateList);
                        eflag = true;
                    }
            }
        });

        //Password
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidPassword(s)){
                    passwordEditText.setError("Please enter a password at least 8 characters long");
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                    ViewCompat.setBackgroundTintList(passwordEditText, colorStateList);
                    pflag = false;
                }
                else {
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                    ViewCompat.setBackgroundTintList(passwordEditText, colorStateList);
                    pflag = true;
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

    private void checkEmailAndPassword(){
        Log.d(TAG, "into the func");
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "into the func");
                        if (task.isSuccessful()) {
                            //eflag=pflag=true;
                            // Sign in success, update UI with the signed-in User's information
                            userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            DocumentReference documentReference =firestore.collection("users").document(userId);
                            documentReference.addSnapshotListener(Login.this,new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    isTeacher = Objects.requireNonNull(documentSnapshot).getBoolean("teacher");
                                    Log.d("Login","is teacher: " + isTeacher);
                                    Intent intent;
                                    if (isTeacher){
                                        Log.d(TAG, "signInWithEmail:success");
                                         intent = new Intent(getApplicationContext(), Teacher_HomePage.class);
                                    }
                                    else {
                                         intent = new Intent(getApplicationContext(), Student_HomePage.class);
                                    }
                                    startActivity(intent);
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the User.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Check your email and password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    protected void Login() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eflag&&pflag) {
                    Vibrator vibrator = (Vibrator) Login.this.getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(5000);
                    checkEmailAndPassword();
                }else{
                    Toast.makeText(Login.this, "Invalid email or password.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {}

}
