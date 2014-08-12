package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.service.IStudentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class StudentViewServiceImpl implements IStudentViewService {

    @Autowired
    private IStudentViewDAO studentViewDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<StudentView> getAll() {
        return studentViewDAO.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<StudentView> getViewByStudentName(String desiredName) {
        return studentViewDAO.getViewByStudentName(desiredName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<StudentView> getView(Map<String, Filter<StudentView>> filterMap) {
        return studentViewDAO.getView(filterMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<StudentView> getViewBySkills(ArrayList<String> ids) {
        return studentViewDAO.getViewBySkills(ids);
    }
}
