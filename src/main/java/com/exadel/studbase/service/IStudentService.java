package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Student;

import java.util.Collection;

public interface IStudentService {
    Student save(Student student);

    Student getById(Long id);

    void delete(Student student);

    Collection<Student> getAll();
}
