package com.exadel.studbase.web.service;

import com.exadel.studbase.web.domain.student.Student;

import java.util.Collection;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IStudentService {
    Student save(Student student);

    Student getById(Long id);

    void delete(Student student);

    Collection<Student> getAll();

    Long getUserId(Long studentId);
}
