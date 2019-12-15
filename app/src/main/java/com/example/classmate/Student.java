package com.example.classmate;

public class Student {
    private String fullName;
    private String email;
    private String phone;
    private String skills;
    private String improve;

    public Student(String _fullName,String _email,String _phone){
        this.fullName=_fullName;
        this.email=_email;
        this.phone=_phone;
    }

    public Student(String _fullName,String _email,String _phone, String _skills,String _improve){
        this.fullName=_fullName;
        this.email=_email;
        this.phone=_phone;
        this.skills = _skills;
        this.improve = _improve;
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
}
