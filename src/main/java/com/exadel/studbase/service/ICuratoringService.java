package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
public interface ICuratoringService {
    Curatoring save(Curatoring curatoring);

    void delete(Curatoring curatoring);

    Collection<Curatoring> getAll();

    Collection<Student> getAllStudentsForEmployee(Long employeeId);

    Collection<Employee> getAllMastersForStudent(Long studentId);
}
