package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IFeedbackDAO;
import com.exadel.studbase.domain.impl.Feedback;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class FeedbackDAOImpl extends GenericDAOImpl<Feedback, StudentView, Long> implements IFeedbackDAO {

    @Override
    public Collection<Feedback> getAllAboutStudent(Long id) {
        Query query = getSession().createQuery("FROM Feedback WHERE student.id =" + id);
        return query.list();
    }

    @Override
    public Collection<Feedback> getAllByEmployee(Long id) {
        Query query = getSession().createQuery("FROM Feedback  WHERE feedbacker.id = " + id);
        return query.list();
    }
}
