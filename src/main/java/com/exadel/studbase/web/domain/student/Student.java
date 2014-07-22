package com.exadel.studbase.web.domain.student;

import com.exadel.studbase.web.domain.IEntity;
import com.exadel.studbase.web.domain.feedback.Feedback;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Алексей on 18.07.14.
 */
@Entity
@Table(name="\"STUDENT\"")
public class Student implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

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
    private Date graduation_date;

    @Column(name="working_hours")
    private int working_hours;

    @Column(name="billable")
    private Date billable;

    @Column(name="role_current_project")
    private String roleCurrentProject;

    @Column(name="techs_current_project")
    private String techsCurrentProject;

    @Column(name="english_level")
    private String englishLevel;

    @OneToMany
    private Collection<Feedback> feedbacks;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
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

    public Date getGraduation_date() {
        return graduation_date;
    }

    public void setGraduation_date(Date graduation_date) {
        this.graduation_date = graduation_date;
    }

    public int getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(int working_hours) {
        this.working_hours = working_hours;
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

    public void setFeedbacks(Collection<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
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
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", hire_date=" + hire_date +
                ", university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", course=" + course +
                ", group=" + group +
                ", graduation_date=" + graduation_date +
                ", working_hours=" + working_hours +
                ", billable=" + billable +
                ", roleCurrentProject='" + roleCurrentProject + '\'' +
                ", techsCurrentProject='" + techsCurrentProject + '\'' +
                ", englishLevel='" + englishLevel + '\'' +
                '}';
    }
}
