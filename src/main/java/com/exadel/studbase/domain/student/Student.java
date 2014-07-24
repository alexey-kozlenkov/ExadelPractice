package com.exadel.studbase.domain.student;

import com.exadel.studbase.domain.IEntity;
import com.exadel.studbase.domain.feedback.Feedback;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Алексей on 18.07.14.
 */
@Entity
@Table(name="\"STUDENT\"")
public class Student implements IEntity<Long> {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name="state")
    private String state;

    @Column(name="hire_date")
    private Date hire_date;

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

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Document> documents;

    public Student() {
        feedbacks = new HashSet<Feedback>();
        documents = new HashSet<Document>();
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
        return hire_date;
    }

    public void setHireDate(Date hireDate) {
        this.hire_date = hireDate;
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

    public void setWorkingHours(int working_hours) {
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

    public Collection<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void addFeedback (Feedback feedback) {
        this.feedbacks.add(feedback);
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
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
                ", hire_date=" + hire_date +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", course=" + course +
                ", group=" + group +
                ", graduation_date=" + graduationDate +
                ", working_hours=" + workingHours +
                ", billable=" + billable +
                ", roleCurrentProject='" + roleCurrentProject + '\'' +
                ", techsCurrentProject='" + techsCurrentProject + '\'' +
                ", englishLevel='" + englishLevel + '\'' +
                '}';
    }
}
