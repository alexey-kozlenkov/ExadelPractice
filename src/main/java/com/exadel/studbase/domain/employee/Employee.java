package com.exadel.studbase.domain.employee;

import com.exadel.studbase.domain.IEntity;
import com.exadel.studbase.domain.feedback.Feedback;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Алексей on 21.07.14.
 */
@Entity
@Table(name="\"EMPLOYEE\"")
public class Employee implements IEntity<Long> {

    @Id
    @Column(name="id")
    private Long id;

    public Employee() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
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
