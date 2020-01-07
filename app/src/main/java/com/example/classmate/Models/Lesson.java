package com.example.classmate.Models;

public class Lesson {
    private String first_student,second_student;

    public Lesson(String first_student,String second_student){
        this.first_student=first_student;
        this.second_student=second_student;
    }

    public void setFirst_student(String first_student){this.first_student=first_student;}
    public String getFirst_student(){return first_student;}
    public void setsecond_student(String second_student){this.second_student=second_student;}
    public String getsecond_student(){return second_student;}

    public String toString(){return first_student+","+second_student;}
}
