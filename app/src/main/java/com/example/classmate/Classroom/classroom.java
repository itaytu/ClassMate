package com.example.classmate.Classroom;

import com.example.classmate.Student.Student;

import java.util.ArrayList;
import java.util.List;

public class classroom {

    private String uuid;
    private String teacher_uuid;
    private List<Student> studentsList;

    public classroom() {
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

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }
}
