package com.example.classmate.Models;

public class User {

    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean isTeacher;

    public User(String fullName, String email, String phoneNumber, boolean isTeacher) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isTeacher = isTeacher;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
