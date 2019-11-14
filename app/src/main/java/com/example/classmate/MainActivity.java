package com.example.classmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private boolean pflag = false, eflag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
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

    protected void Login() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pflag && eflag){
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                }
                else {
                    loginButton.setError("Please check your Email and Password");
                }
            }
        });


    }

}
