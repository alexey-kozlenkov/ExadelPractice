package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Feedback;

import java.util.Collection;

public interface IFeedbackService {
    Feedback save(Feedback feedback);

    Feedback getById(Long id);

    void delete(Feedback feedback);

    Collection<Feedback> getAllAboutStudent(Long studentId);
}
