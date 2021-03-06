package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.*;
import java.sql.Date;

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

    @ManyToOne
    @JoinColumn(name = "university")
    private University university;

    @ManyToOne
    @JoinColumn(name = "faculty")
    private Faculty faculty;

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

    @Column(name = "term_marks")
    private String termMarks;

    @Column(name = "current_project")
    private String currentProject;

    @Column(name = "team_lead_current_project")
    private Long teamLeadId;

    @Column(name = "project_manager_current_project")
    private Long projectManagerId;

    @Column(name = "training_before_working")
    private Boolean trainingBeforeStartWorking;

    @Column(name = "course_when_start_working")
    private Integer courseWhenStartWorking;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "wishes_hours_number")
    private Integer wishesHoursNumber;

    @Column(name = "trainings_exadel")
    private String trainingsInExadel;

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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
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

    public Integer getEnglishLevel() {
        return  englishLevel;
    }

    public void setEnglishLevel(Integer englishLevel) {
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

    public Boolean getTrainingBeforeStartWorking() {
        return trainingBeforeStartWorking;
    }

    public void setTrainingBeforeStartWorking(Boolean trainingBeforeStartWorking) {
        this.trainingBeforeStartWorking = trainingBeforeStartWorking;
    }

    public Integer getCourseWhenStartWorking() {
        return courseWhenStartWorking;
    }

    public void setCourseWhenStartWorking(Integer courseWhenStartWorking) {
        this.courseWhenStartWorking = courseWhenStartWorking;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getWishesHoursNumber() {
        return wishesHoursNumber;
    }

    public void setWishesHoursNumber(Integer wishesHoursNumber) {
        this.wishesHoursNumber = wishesHoursNumber;
    }

    public String getTrainingsInExadel() {
        return trainingsInExadel;
    }

    public void setTrainingsInExadel(String trainingsInExadel) {
        this.trainingsInExadel = trainingsInExadel;
    }

    public void clearExadelInfo() {
        this.hireDate = null;
        this.workingHours = null;
        this.billable = null;
        this.roleCurrentProject = "";
        this.techsCurrentProject = "";
        this.currentProject = "";
        this.teamLeadId = null;
        this.projectManagerId = null;
        this.trainingBeforeStartWorking = null;
        this.courseWhenStartWorking = null;
        this.wishesHoursNumber = null;
        this.trainingsInExadel = "";
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
