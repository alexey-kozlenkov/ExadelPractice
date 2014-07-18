package com.exadel.studbase.domain.entity;

import java.sql.Date;

public class Student extends User {
    private String state;
    private Date hireDate;
    private String university;
    private String faculty;
    private int course;
    private int studentGroup;
    private Date graduationDate;
    private int wokingHours;
    private Date billable;
    private String roleCurrentProject;
    private String technical;
    private String technologyCurrentProject;
    private String englishLevel;

    public Student() {
        super();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(int studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public int getWokingHours() {
        return wokingHours;
    }

    public void setWokingHours(int wokingHours) {
        this.wokingHours = wokingHours;
    }

    public Date getBillable() {
        return billable;
    }

    public void setBillable(Date billable) {
        this.billable = billable;
    }

    public String getRoleCurrentProject() {
        return roleCurrentProject;
    }

    public void setRoleCurrentProject(String roleCurrentProject) {
        this.roleCurrentProject = roleCurrentProject;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getTechnologyCurrentProject() {
        return technologyCurrentProject;
    }

    public void setTechnologyCurrentProject(String technologyCurrentProject) {
        this.technologyCurrentProject = technologyCurrentProject;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;
    }
}