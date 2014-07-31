package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
@Repository
public class StudentViewDAO extends GenericDAOImpl<StudentView, Long> implements IStudentViewDAO {
    @Override
    public Collection<StudentView> getViewByStudentName(String desiredName) {
        Query query = getSession()
                .createSQLQuery("SELECT a.* FROM find_student_by_name('" + desiredName + "' ) as a")
                .addEntity("a", StudentView.class);
        return query.list();
    }
}
