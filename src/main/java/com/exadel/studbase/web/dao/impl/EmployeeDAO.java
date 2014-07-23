package com.exadel.studbase.web.dao.impl;

import com.exadel.studbase.web.dao.impl.GenericDAOImpl;
import com.exadel.studbase.web.dao.IEmployeeDAO;
import com.exadel.studbase.web.domain.employee.Employee;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 21.07.14.
 */
@Repository
public class EmployeeDAO extends GenericDAOImpl<Employee, Long> implements IEmployeeDAO {
}
