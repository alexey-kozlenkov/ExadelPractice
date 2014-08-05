package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
public interface ICuratoringDAO extends GenericDAO<Curatoring, Long> {
    Collection<Student> getAllStudentsForEmployee(Long employeeId);

    Collection<Employee> getAllMastersForStudent(Long studentId);
}