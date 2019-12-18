package com.example.classmate.Models;

import java.util.ArrayList;

public class Teacher {

    private String fullName;
    private String email;
    private String phone;

    private ArrayList<String> classes;


    public Teacher(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;

        classes = new ArrayList<>();
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void addClasses(String classroom) {
        this.classes.add(classroom);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
