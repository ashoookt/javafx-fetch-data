package com.infoman.model;

public class Student {
    private String fname;
    private String mname;
    private String lname;
    private String number;
    private String email;
    private String gender;

    public Student(String fname, String mname, String lname, String number, String email, String gender) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.number= number;
        this.email = email;
        this.gender = gender;

    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNumber() {
        return number;
    }

    public void setNum(String num) {
        this.number = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNumber(String text) {
    }
}
