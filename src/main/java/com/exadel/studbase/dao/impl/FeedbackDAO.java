package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IFeedbackDAO;
import com.exadel.studbase.domain.feedback.Feedback;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
@Repository
public class FeedbackDAO extends GenericDAOImpl<Feedback, Long> implements IFeedbackDAO {

    @Override
    public Collection<Feedback> getAllAboutStudent(Long id) {
        Query query = getSession().createQuery("FROM Feedback WHERE studentId="+id);

        return query.list();
    }
}
