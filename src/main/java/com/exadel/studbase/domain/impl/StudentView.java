package com.exadel.studbase.domain.impl;

import com.exadel.studbase.dao.IEmployeeDAO;
import com.exadel.studbase.domain.IEntity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Алексей on 28.07.2014.
 */
@Entity
@Table(name="\"STUDENT_VIEW\"")
public class StudentView implements IEntity<Long>{

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="hire_date")
    private Date hireDate;

    @Column(name="university")
    private String university;

    @Column(name="faculty")
    private String faculty;

    @Column(name="course")
    private int course;

    @Column(name="s_group")
    private int group;

    @Column(name="graduation_date")
    private Date graduationDate;

    @Column(name="working_hours")
    private int workingHours;

    @Column(name="billable")
    private Date billable;

    @Column(name="role_current_project")
    private String roleCurrentProject;

    @Column(name="techs_current_project")
    private String techsCurrentProject;

    @Column(name="english_level")
    private String englishLevel;

    public StudentView() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
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

    public String getTechsCurrentProject() {
        return techsCurrentProject;
    }

    public void setTechsCurrentProject(String techsCurrentProject) {
        this.techsCurrentProject = techsCurrentProject;
    }

    public String getEnglishLevel() {
        return englishLevel;
    }

    public void setEnglishLevel(String englishLevel) {
        this.englishLevel = englishLevel;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentView that = (StudentView) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
