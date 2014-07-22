package com.exadel.studbase.web.dao;

import com.exadel.studbase.web.domain.student.Student;

/**
 * Created by Алексей on 18.07.14.
 */
public interface IStudentDAO extends GenericDAO<Student, Long> {

    Long getUser (Long studentId);

}
