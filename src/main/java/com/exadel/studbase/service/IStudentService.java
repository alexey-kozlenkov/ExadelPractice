package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Student;

import java.util.Collection;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IStudentService {
    Student save(Student student);

    Student getById(Long id);

    void delete(Student student);

    Collection<Student> getAll();
}
