package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Feedback;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

public interface IFeedbackDAO extends GenericDAO<Feedback, StudentView, Long> {
    Collection<Feedback> getAllAboutStudent(Long studentId);

    Collection<Feedback> getAllByEmployee(Long employeeId);

    void addFeedbacksWhenAppointingCurators(Long[] studentsIds, Long[] curatorsIds);
}
