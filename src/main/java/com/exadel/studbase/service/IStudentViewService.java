package com.exadel.studbase.service;

import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface IStudentViewService {
    Collection<StudentView> getAll();

    Collection<StudentView> getViewByStudentName(String desiredName);

    Collection<StudentView> getView(Map<String, Filter<StudentView>> filterMap);

    Collection<StudentView> getViewBySkills(ArrayList<String> ids);
}
