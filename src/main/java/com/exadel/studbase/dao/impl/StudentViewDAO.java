package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.domain.impl.StudentView;
import org.springframework.stereotype.Repository;

/**
 * Created by ala'n on 29.07.2014.
 */
@Repository
public class StudentViewDAO extends GenericDAOImpl<StudentView, Long> implements IStudentViewDAO {
}
