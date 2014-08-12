package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

public interface IEmployeeDAO extends GenericDAO<Employee, StudentView, Long> {
    Collection<User> getAllCurators();
}
