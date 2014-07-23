package com.exadel.studbase.web.service;

import com.exadel.studbase.web.domain.feedback.Feedback;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
public interface IFeedbackService {
    Feedback save(Feedback feedback);

    Feedback getById (Long id);

    void delete(Feedback feedback);

    Collection<Feedback> getAllAboutStudent(Long studentId);
}
