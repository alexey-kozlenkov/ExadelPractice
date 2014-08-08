package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
public interface IStudentViewService {
    Collection<StudentView> getAll();

    Collection<StudentView> getViewByStudentName(String desiredName);
}
