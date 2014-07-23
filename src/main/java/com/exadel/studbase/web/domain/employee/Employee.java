package com.exadel.studbase.web.domain.employee;

import com.exadel.studbase.web.domain.IEntity;
import com.exadel.studbase.web.domain.feedback.Feedback;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
@Entity
@Table(name="\"EMPLOYEE\"")
public class Employee implements IEntity<Long> {

    @Id
    @Column(name="id")
    private Long id;

    @OneToMany(mappedBy = "feedbacker", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Feedback> feedbacks;

    public Employee() {
        feedbacks = new ArrayList<Feedback>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    public Collection<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Collection<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public void addFeedback (Feedback feedback) {
        this.feedbacks.add(feedback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!id.equals(employee.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                '}';
    }
}
