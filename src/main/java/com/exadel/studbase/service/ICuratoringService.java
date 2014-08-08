package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
public interface ICuratoringService {
    public Curatoring save(Curatoring curatoring);

    public void delete(Curatoring curatoring);

    public Collection<Curatoring> getAll();

    Collection<com.exadel.studbase.domain.impl.StudentView> getAllStudentsForEmployee(Long employeeId);

    Collection<Employee> getAllMastersForStudent(Long studentId);
}
