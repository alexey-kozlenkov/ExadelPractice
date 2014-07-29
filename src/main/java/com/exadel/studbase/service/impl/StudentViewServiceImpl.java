package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IStudentViewDAO;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.service.IStudentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by ala'n on 29.07.2014.
 */
@Service
public class StudentViewServiceImpl implements IStudentViewService{

    @Autowired
    private IStudentViewDAO studentViewDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Collection<StudentView> getAll() {
        return studentViewDAO.getAll();
    }
}
