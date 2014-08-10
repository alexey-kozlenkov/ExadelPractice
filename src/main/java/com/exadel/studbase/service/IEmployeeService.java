package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

public interface IEmployeeService {
    Employee save(Employee employee);

    Employee getById(Long id);

    void delete(Employee employee);

    Collection<User> getAllCurators();

    Collection<Employee> getAll();
}
