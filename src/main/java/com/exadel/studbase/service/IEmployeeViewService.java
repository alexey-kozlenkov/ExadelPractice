package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.EmployeeView;

import java.util.Collection;

public interface IEmployeeViewService {
    EmployeeView save(EmployeeView employeeView);

    EmployeeView getById(Long id);

    void delete (EmployeeView employeeView);

    Collection<EmployeeView> getAll();
}
