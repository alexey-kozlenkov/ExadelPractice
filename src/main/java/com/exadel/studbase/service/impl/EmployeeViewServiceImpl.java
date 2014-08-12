package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IEmployeeViewDAO;
import com.exadel.studbase.domain.impl.EmployeeView;
import com.exadel.studbase.service.IEmployeeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class EmployeeViewServiceImpl implements IEmployeeViewService {

    @Autowired
    IEmployeeViewDAO employeeViewDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeView save(EmployeeView employeeView) {
        return employeeViewDAO.saveOrUpdate(employeeView);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeView getById(Long id) {
        return employeeViewDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(EmployeeView employeeView) {
        employeeViewDAO.delete(employeeView);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<EmployeeView> getViewByEmployeeName(String desiredName) {
        return employeeViewDAO.getViewByEmployeeName(desiredName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<EmployeeView> getAll() {
        return employeeViewDAO.getAll();
    }
}
