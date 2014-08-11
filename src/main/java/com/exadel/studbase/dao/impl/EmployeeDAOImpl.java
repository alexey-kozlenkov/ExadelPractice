package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IEmployeeDAO;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, StudentView, Long> implements IEmployeeDAO {
    @Override
    public Collection<User> getAllCurators() {
        Query query = getSession().createQuery("FROM User WHERE role=" + "\'ROLE_CURATOR\'");
        return query.list();
    }
}
