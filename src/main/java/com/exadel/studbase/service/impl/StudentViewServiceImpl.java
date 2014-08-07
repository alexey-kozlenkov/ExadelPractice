package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.service.IStudentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ala'n on 29.07.2014.
 */
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
    public List<StudentView> getView(Map<String, Filter<StudentView>> filterMap) {
        return studentViewDAO.getView(filterMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<StudentView> filterBySkillTypeId(Long[] ids) {
        return studentViewDAO.filterBySkillTypeId(ids);
    }
}
