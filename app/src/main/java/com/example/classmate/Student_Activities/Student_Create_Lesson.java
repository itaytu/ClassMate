package com.example.classmate.Student_Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classmate.Models.Request;
import com.example.classmate.Models.Student;
import com.example.classmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Student_Create_Lesson extends AppCompatActivity implements View.OnClickListener {

    private Button btnDatePicker, btnTimePicker, btnSubmit;
    private EditText txtDate, txtTime;
    private Spinner spinner;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String requesting_student_id;
    private String responding_student_id;
    private String lesson_subject;
    private Date lesson_date;

    private int mYear = 0, mMonth = 0, mDay = 0, mHour = 0, mMinute = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__create__lesson);

        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        btnSubmit = findViewById(R.id.button_submit);
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser().getUid() != null) {
            requesting_student_id = fAuth.getCurrentUser().getUid();

            final Student second_student = (Student) getIntent().getSerializableExtra("st");

            CollectionReference collectionReference = fStore.collection("students");
            final Query query = collectionReference.whereEqualTo("email", Objects.requireNonNull(second_student).getEmail());

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                            responding_student_id = documentSnapshot.getId();
                            Log.d("SECOND STUDENT ID: ", documentSnapshot.getId());
                        }

                        spinner = findViewById(R.id.lessons_spinner);

                        ArrayAdapter<String> lessons_adapter = new ArrayAdapter<>(Student_Create_Lesson.this, android.R.layout.simple_spinner_item, second_student.getSkills());
                        lessons_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(lessons_adapter);

                    } else
                        Log.d("ERROR NO STUDENT: ", task.getException().toString());
                }
            });
        }
    }
    //TODO now work
    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            String dateString = dayOfMonth + " " + monthOfYear + " " + year;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                            Date date = new Date();
                            try {
                                date = sdf.parse(dateString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txtDate.setText(sdf.format(Objects.requireNonNull(date)));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            mHour = hourOfDay;
                            mMinute = minute;
                            String dateString = hourOfDay + " " + minute;
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                            Date date = new Date();
                            try {
                                date = dateFormat.parse(dateString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txtTime.setText(dateFormat.format(Objects.requireNonNull(date)));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mYear);
        cal.set(Calendar.MONTH, mMonth);
        cal.set(Calendar.DAY_OF_MONTH, mDay);
        cal.set(Calendar.HOUR_OF_DAY, mHour);
        cal.set(Calendar.MINUTE, mMinute);
        lesson_date = cal.getTime();

        if (v == btnSubmit) {
            if(txtDate.getText().toString().matches("") || txtTime.getText().toString().matches("")){
                btnSubmit.setError("Please choose date and time");
            }
            else {
                DocumentReference docFirst = fStore.collection("students").document(requesting_student_id);
                DocumentReference docSec = fStore.collection("students").document(responding_student_id);

                lesson_subject = spinner.getSelectedItem().toString();
                final Request request = new Request(requesting_student_id, responding_student_id, lesson_subject, lesson_date);
                final DocumentReference documentReferenceRequests = fStore.collection("requests").document();

                documentReferenceRequests.set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("REQUEST: ", "Request created successfully ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("REQUEST: ", "Request creation failed");
                    }
                });

                docSec.update("myRequests", FieldValue.arrayUnion(documentReferenceRequests.getId()));

                Intent intent = new Intent(this.getApplicationContext(), Student_HomePage.class);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this.getApplicationContext(), Student_HomePage.class);
        startActivity(intent);
    }
}

