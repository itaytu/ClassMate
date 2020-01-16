package com.example.classmate.Models;

import java.util.Date;

public class Lesson {

    private String first_student;
    private String second_student;
    private String subject;
    private Date lesson_date;

    public Lesson(String first_student, String second_student,String subject, Date lesson_date){
        this.first_student = first_student;
        this.second_student = second_student;
        this.subject = subject;
        this.lesson_date = lesson_date;
    }

    public Lesson(String first_student, String second_student){
        this.first_student = first_student;
        this.second_student = second_student;
    }

    public void setFirst_student(String first_student){this.first_student=first_student;}

    public String getFirst_student(){return first_student;}

    public void setsecond_student(String second_student){this.second_student=second_student;}
    public String getsecond_student(){return second_student;}



    public String toString(){
        return first_student + "," + second_student;
    }

    public Date getLesson_date() {
        return lesson_date;
    }

    public void setLesson_date(Date lesson_date) {
        this.lesson_date = lesson_date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}