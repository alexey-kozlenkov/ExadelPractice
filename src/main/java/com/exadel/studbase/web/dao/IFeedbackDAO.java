package com.exadel.studbase.web.dao;

import com.exadel.studbase.web.domain.feedback.Feedback;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
public interface IFeedbackDAO extends GenericDAO<Feedback, Long> {
    Collection<Feedback> getAllAboutStudent(Long studentId);
}
