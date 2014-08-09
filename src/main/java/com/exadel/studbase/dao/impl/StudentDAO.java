package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentDAO;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.domain.impl.StudentView;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 18.07.14.
 */
@Repository
public class StudentDAO extends GenericDAOImpl<Student, StudentView, Long> implements IStudentDAO {
}
