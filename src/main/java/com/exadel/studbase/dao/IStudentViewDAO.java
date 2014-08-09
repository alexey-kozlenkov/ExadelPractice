package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.StudentView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
public interface IStudentViewDAO extends GenericDAO<StudentView, StudentView, Long> {
    public Collection<StudentView> getViewByStudentName(String desiredName);

    public Collection<StudentView> filterBySkillTypeId (ArrayList<String> ids);
}
