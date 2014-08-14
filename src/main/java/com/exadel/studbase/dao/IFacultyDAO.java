package com.exadel.studbase.dao;

import com.exadel.studbase.dao.GenericDAO;
import com.exadel.studbase.domain.impl.Faculty;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

public interface IFacultyDAO extends GenericDAO<Faculty, StudentView, Long> {
    Collection<Faculty> getAllForUniversity(Long universityId);
}
