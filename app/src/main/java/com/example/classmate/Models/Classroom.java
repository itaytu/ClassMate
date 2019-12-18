package com.example.classmate.Models;

import java.util.ArrayList;
import java.util.List;

public class Classroom {

    private String uuid;
    private String teacher_uuid;
    private String class_name;

    private List<Student> studentsList;

    public Classroom(String class_name) {
        this.class_name = class_name;

        studentsList = new ArrayList<>();
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTeacher_uuid() {
        return teacher_uuid;
    }

    public void setTeacher_uuid(String teacher_uuid) {
        this.teacher_uuid = teacher_uuid;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void addStudents(Student student) {
        this.studentsList.add(student);
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
