package com.example.classmate.Models;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {


    private boolean isAccepted;
    private String lesson_subject;
    private String first_student;
    private String second_student;

    private Date lesson_date;


    public Request(String first_student, String second_student, String lesson_subject, Date lesson_date){
        this.first_student = first_student;
        this.second_student = second_student;
        this.lesson_subject = lesson_subject;

        this.lesson_date = lesson_date;
    }

    public Date getLesson_date() {
        return lesson_date;
    }

    public void setLesson_date(Date lesson_date) {
        this.lesson_date = lesson_date;
    }

    public String getSecond_student() {
        return second_student;
    }

    public void setSecond_student(String second_student) {
        this.second_student = second_student;
    }

    public String getFirst_student() {
        return first_student;
    }

    public void setFirst_student(String first_student) {
        this.first_student = first_student;
    }

    public String getLesson_subject() {
        return lesson_subject;
    }

    public void setLesson_subject(String lesson_subject) {
        this.lesson_subject = lesson_subject;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }


}
