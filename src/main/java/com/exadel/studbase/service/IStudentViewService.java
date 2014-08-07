package com.exadel.studbase.service;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ala'n on 29.07.2014.
 */
public interface IStudentViewService {
    public Collection<StudentView> getAll();

    public Collection<StudentView> getViewByStudentName(String desiredName);

    public List<StudentView> getView(Map<String, Filter<StudentView>> filterMap);

    public Collection<StudentView> filterBySkillTypeId (Long[] ids);
}
