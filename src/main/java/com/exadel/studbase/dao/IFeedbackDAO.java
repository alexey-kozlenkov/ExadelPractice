package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Feedback;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
public interface IFeedbackDAO extends GenericDAO<Feedback, StudentView, Long> {
    public Collection<Feedback> getAllAboutStudent(Long studentId);
}
