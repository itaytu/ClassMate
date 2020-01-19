package com.example.classmate.Models;

import java.util.Date;

public class Lesson {

    private String teacher;
    private String student;
    private String subject;
    private Date lesson_date;

    public Lesson(String teacher, String student, String subject, Date lesson_date){
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.lesson_date = lesson_date;
    }

    public Lesson(String teacher, String student){
        this.teacher = teacher;
        this.student = student;
    }

    public void setTeacher(String teacher){this.teacher = teacher;}

    public String getTeacher(){return teacher;}

    public void setsecond_student(String second_student){this.student =second_student;}
    public String getsecond_student(){return student;}



    public String toString(){
        return teacher + "," + student;
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