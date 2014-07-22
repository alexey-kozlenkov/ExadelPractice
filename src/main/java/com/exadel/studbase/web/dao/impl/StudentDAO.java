package com.exadel.studbase.web.dao.impl;

import com.exadel.studbase.web.dao.impl.GenericDAOImpl;
import com.exadel.studbase.web.dao.IStudentDAO;
import com.exadel.studbase.web.domain.student.Student;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;



/**
 * Created by Алексей on 18.07.14.
 */
@Repository
public class StudentDAO extends GenericDAOImpl <Student, Long> implements IStudentDAO {
    @Override
    public Long getUser(Long studentId) {

        Query query = getSession().createQuery("SELECT id FROM User WHERE studentInfo.id=" + studentId);
        return (Long) query.list().get(0);
    }
}
