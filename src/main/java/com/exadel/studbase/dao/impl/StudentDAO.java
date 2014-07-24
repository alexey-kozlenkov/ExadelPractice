package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentDAO;
import com.exadel.studbase.domain.student.Student;
import org.springframework.stereotype.Repository;



/**
 * Created by Алексей on 18.07.14.
 */
@Repository
public class StudentDAO extends GenericDAOImpl <Student, Long> implements IStudentDAO {
}
