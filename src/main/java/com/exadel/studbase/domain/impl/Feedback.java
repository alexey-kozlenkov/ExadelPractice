package com.exadel.studbase.domain.impl;

import com.exadel.studbase.domain.IEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "\"FEEDBACK\"")
public class Feedback implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "employee_id")
    private Long feedbackerId;

    @Column(name = "content")
    private String content;

    @Column(name = "professional_competence")
    private String professionalCompetence;

    @Column(name = "attitude_to_work")
    private String attitudeToWork;

    @Column(name = "collective_relations")
    private String collectiveRelations;

    @Column(name = "professional_progress")
    private String professionalProgress;

    @Column(name = "need_more_hours")
    private boolean needMoreHours;

    @Column(name = "is_on_project")
    private boolean isOnProject;

    @Column(name = "feedback_date")
    private Date feedbackDate;

    public Feedback() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getFeedbackerId() {
        return feedbackerId;
    }

    public void setFeedbacker(Long feedbackerId) {
        this.feedbackerId = feedbackerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProfessionalCompetence() {
        return professionalCompetence;
    }

    public void setProfessionalCompetence(String professionalCompetence) {
        this.professionalCompetence = professionalCompetence;
    }

    public String getAttitudeToWork() {
        return attitudeToWork;
    }

    public void setAttitudeToWork(String attitudeToWork) {
        this.attitudeToWork = attitudeToWork;
    }

    public String getCollectiveRelations() {
        return collectiveRelations;
    }

    public void setCollectiveRelations(String collectiveRelations) {
        this.collectiveRelations = collectiveRelations;
    }

    public String getProfessionalProgress() {
        return professionalProgress;
    }

    public void setProfessionalProgress(String professionalProgress) {
        this.professionalProgress = professionalProgress;
    }

    public boolean isNeedMoreHours() {
        return needMoreHours;
    }

    public void setNeedMoreHours(boolean needMoreHours) {
        this.needMoreHours = needMoreHours;
    }

    public boolean isOnProject() {
        return isOnProject;
    }

    public void setOnProject(boolean isOnProject) {
        this.isOnProject = isOnProject;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (!id.equals(feedback.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", professionalCompetence='" + professionalCompetence + '\'' +
                ", attitudeToWork='" + attitudeToWork + '\'' +
                ", collectiveRelations='" + collectiveRelations + '\'' +
                ", professionalProgress='" + professionalProgress + '\'' +
                ", needMoreHours=" + needMoreHours +
                ", isOnProject=" + isOnProject +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}
