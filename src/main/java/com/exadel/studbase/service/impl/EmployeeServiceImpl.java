package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IEmployeeDAO;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
@Service
public class EmployeeServiceImpl  implements IEmployeeService{

    @Autowired
    private IEmployeeDAO employeeDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Employee save(Employee employee) {
        return employeeDAO.saveOrUpdate(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Employee getById(Long id) {
        return employeeDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Employee employee) {
        employeeDAO.delete(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Employee> getAll() {
        return employeeDAO.getAll();
    }
}
