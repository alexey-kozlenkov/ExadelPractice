package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

public interface ICuratoringService {
    Curatoring save(Curatoring curatoring);

    void delete(Curatoring curatoring);

    Collection<Curatoring> getAll();

    Collection<StudentView> getAllStudentsForEmployee(Long employeeId);

    Collection<Employee> getAllMastersForStudent(Long studentId);

    void appointCuratorsToStudents(Long[] studentsIds, Long[] curatorsIds);
}
