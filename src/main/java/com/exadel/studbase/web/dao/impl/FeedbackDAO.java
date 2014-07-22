package com.exadel.studbase.web.dao.impl;

import com.exadel.studbase.web.dao.IFeedbackDAO;
import com.exadel.studbase.web.domain.feedback.Feedback;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Алексей on 21.07.14.
 */
@Repository
public class FeedbackDAO extends GenericDAOImpl<Feedback, Long> implements IFeedbackDAO {

    @Override
    public Collection<Feedback> getAllAboutStudent(Long id) {
        Query query = getSession().createQuery("FROM Feedback WHERE student.id="+id);

        return query.list();
    }
}
