package com.example.classmate.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {

    private String fullName;
    private String email;
    private String phone;
    private String classroom;

    private ArrayList<String> skills;
    private ArrayList<String> weaknesses;

    private ArrayList<String> myRequests;
    private ArrayList<String> myLessons;


    public Student(String fullName, String email, String phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.classroom="";

        skills = new ArrayList<>();
        weaknesses = new ArrayList<>();
        myLessons = new ArrayList<>();
        myRequests = new ArrayList<>();
    }

    public Student(Student student) {
        this.fullName = student.fullName;
        this.email = student.email;
        this.phone = student.phone;

        skills = new ArrayList<>();
        for(String skill : student.skills) {
            skills.add(skill);
        }

        weaknesses = new ArrayList<>();
        for(String weakness : student.weaknesses) {
            weaknesses.add(weakness);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void addSkills(String skill) {
        this.skills.add(skill);
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public void addWeakness(String weakness) {
        this.weaknesses.add(weakness);
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
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

    @NonNull
    @Override
    public String toString() {
        return this.getFullName()+" , " + this.getSkills()+" , " + this.getWeaknesses();
    }

    public ArrayList<String> getMyLessons() {
        return myLessons;
    }

    public void setMyLessons(ArrayList<String> myLessons) {
        this.myLessons = myLessons;
    }

    public ArrayList<String> getMyRequests() {
        return myRequests;
    }

    public void setMyRequests(ArrayList<String> myRequests) {
        this.myRequests = myRequests;
    }
}
