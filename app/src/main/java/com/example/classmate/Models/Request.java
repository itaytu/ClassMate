package com.example.classmate.Models;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {


    private boolean isAccepted;
    private String lesson_subject;
    private String requesting_student;
    private String responding_student;

    private Date lesson_date;


    public Request(String first_student, String second_student, String lesson_subject, Date lesson_date){
        this.requesting_student = first_student;
        this.responding_student = second_student;
        this.lesson_subject = lesson_subject;

        this.lesson_date = lesson_date;
    }

    public Date getLesson_date() {
        return lesson_date;
    }

    public void setLesson_date(Date lesson_date) {
        this.lesson_date = lesson_date;
    }

    public String getResponding_student() {
        return responding_student;
    }

    public void setResponding_student(String responding_student) {
        this.responding_student = responding_student;
    }

    public String getRequesting_student() {
        return requesting_student;
    }

    public void setRequesting_student(String requesting_student) {
        this.requesting_student = requesting_student;
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
