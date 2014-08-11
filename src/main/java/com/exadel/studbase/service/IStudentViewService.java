package com.exadel.studbase.service;

import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface IStudentViewService {
    public Collection<StudentView> getAll();

    public Collection<StudentView> getViewByStudentName(String desiredName);

    public Collection<StudentView> getView(Map<String, Filter<StudentView>> filterMap);

    public Collection<StudentView> getViewBySkills(ArrayList<String> ids);
}
