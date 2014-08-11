package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "\"STUDENT_VIEW\"")
public class StudentView implements IEntity<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "university")
    private String university;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "course")
    private Integer course;

    @Column(name = "s_group")
    private Integer group;

    @Column(name = "graduation_date")
    private Integer graduationDate;

    @Column(name = "working_hours")
    private Integer workingHours;

    @Column(name = "billable")
    private Date billable;

    @Column(name = "role_current_project")
    private String roleCurrentProject;

    @Column(name = "techs_current_project")
    private String techsCurrentProject;

    @Column(name = "english_level")
    private Integer englishLevel;

    public StudentView() {
        course = 0;
        group = 0;
        workingHours = 0;
        graduationDate = 0;
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

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Integer graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
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
        switch (englishLevel) {
            case 0:
                return "Beginner";
            case 1:
                return "Elementary";
            case 2:
                return "Pre-Intermediate";
            case 3:
                return "Intermediate";
            case 4:
                return "Upper-Intermediate";
            case 5:
                return "Advanced";
            default:
                return "undefined";
        }
    }

    public void setEnglishLevel(String englishLevel) {

        englishLevel = englishLevel.toLowerCase();

        if (englishLevel.equals("beginner")) {
            this.englishLevel = 0;
        } else if (englishLevel.equals("elementary")) {
            this.englishLevel = 1;
        } else if (englishLevel.equals("pre-intermediate")) {
            this.englishLevel = 2;
        } else if (englishLevel.equals("intermediate")) {
            this.englishLevel = 3;
        } else if (englishLevel.equals("upper-intermediate")) {
            this.englishLevel = 4;
        } else if (englishLevel.equals("advanced")) {
            this.englishLevel = 5;
        } else {
            this.englishLevel = -1;
        }
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
