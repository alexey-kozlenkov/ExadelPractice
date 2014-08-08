package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

/**
 * Created by Алексей on 21.07.14.
 */
public interface IEmployeeDAO extends GenericDAO<Employee, Long> {
    Collection<User> getAllCurators();
}
