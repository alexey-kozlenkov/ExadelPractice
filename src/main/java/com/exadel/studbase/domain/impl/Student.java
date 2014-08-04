package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by Алексей on 18.07.14.
 */
@Entity
@Table(name = "\"STUDENT\"")
public class Student implements IEntity<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "state")
    private String state;

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
    private Date graduationDate;

    @Column(name = "working_hours")
    private Integer workingHours;

    @Column(name = "billable")
    private Date billable;

    @Column(name = "role_current_project")
    private String roleCurrentProject;

    @Column(name = "techs_current_project")
    private String techsCurrentProject;

    @Column(name = "english_level")
    private String englishLevel;

    @Column(name="term_marks")
    private String termMarks;

    @Column(name="current_project")
    private String currentProject;

    @Column(name="team_lead_current_project")
    private Long teamLeadId;

    @Column(name="project_manager_current_project")
    private Long projectManagerId;


    public Student() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer working_hours) {
        this.workingHours = working_hours;
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

    public String getTermMarks() {
        return termMarks;
    }

    public void setTermMarks(String termMarks) {
        this.termMarks = termMarks;
    }

    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    public Long getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(Long teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!id.equals(student.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", hire_date=" + hireDate +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", course=" + course +
                ", group=" + group +
                ", graduationDate=" + graduationDate +
                ", workingHours=" + workingHours +
                ", billable=" + billable +
                ", roleCurrentProject='" + roleCurrentProject + '\'' +
                ", techsCurrentProject='" + techsCurrentProject + '\'' +
                ", englishLevel='" + englishLevel + '\'' +
                '}';
    }
}
