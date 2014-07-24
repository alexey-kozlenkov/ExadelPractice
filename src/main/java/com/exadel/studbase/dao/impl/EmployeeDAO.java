package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IEmployeeDAO;
import com.exadel.studbase.domain.employee.Employee;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 21.07.14.
 */
@Repository
public class EmployeeDAO extends GenericDAOImpl<Employee, Long> implements IEmployeeDAO {
}
