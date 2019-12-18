package com.example.classmate.Student;

public class Student {

    private String uuid;

    private String fullName;
    private String email;
    private String phone;
    private String skills;
    private String improve;
    private boolean isClicked;

    public Student(String _uuid, String _fullName, String _email, String _phone){
        this.fullName=_fullName;
        this.email=_email;
        this.phone=_phone;

        this.uuid = _uuid;
    }

    public Student(String _uuid, String _fullName, String _email, String _phone, String _skills){
        this.fullName=_fullName;
        this.email=_email;
        this.phone=_phone;
        this.skills = _skills;

        this.uuid = _uuid;
    }

    public Student(String _uuid, String _fullName, String _email, String _phone, String _skills, String _improve){
        this.fullName=_fullName;
        this.email=_email;
        this.phone=_phone;
        this.skills = _skills;
        this.improve = _improve;

        this.uuid = _uuid;
    }


    public String getFullName() {
        return "Name : "+fullName;
    }

    public String getPhone() {
        return "Phone : "+phone;
    }

    public String getEmail() {
        return "Email : "+email;
    }

    public String getSkills() {
        return "Skills : "+skills;
    }

    public String getImprove() {
        return "Improve : "+improve;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
