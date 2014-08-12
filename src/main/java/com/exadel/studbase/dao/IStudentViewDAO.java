package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.StudentView;

import java.util.ArrayList;
import java.util.Collection;

public interface IStudentViewDAO extends GenericDAO<StudentView, StudentView, Long> {
    Collection<StudentView> getViewByStudentName(String desiredName);

    Collection<StudentView> getViewBySkills(ArrayList<String> ids);
}
