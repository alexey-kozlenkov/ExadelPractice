package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
@Repository
public class StudentViewDAO extends GenericDAOImpl<StudentView, Long> implements IStudentViewDAO {
    @Override
    public Collection<StudentView> getViewByStudentName(String desiredName) {
        Query query = getSession().createSQLQuery("SELECT * FROM find_student_by_name('" + desiredName + "')");
        return query.list();
    }
}
