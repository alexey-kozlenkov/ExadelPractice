package com.exadel.studbase.service;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by ala'n on 29.07.2014.
 */
public interface IStudentViewService {
    Collection<StudentView> getAll();

    Collection<StudentView> getViewByStudentName(String desiredName);

    Collection<StudentView> getView(Map<String, Filter<StudentView>> filterMap);

    Collection<StudentView> filterBySkillTypeId (ArrayList<String> ids);
}
