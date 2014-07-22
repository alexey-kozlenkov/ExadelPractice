package com.exadel.studbase.web.service;

import com.exadel.studbase.web.domain.employee.Employee;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
public interface IEmployeeService {
    Employee save(Employee employee);

    Employee getById(Long id);

    void delete(Employee employee);

    Collection<Employee> getAll();

}
