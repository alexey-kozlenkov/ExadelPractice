package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IEmployeeViewDAO;
import com.exadel.studbase.domain.impl.EmployeeView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class EmployeeViewDAOImpl extends GenericDAOImpl<EmployeeView, EmployeeView, Long> implements IEmployeeViewDAO {
    @Override
    public Collection<EmployeeView> getViewByEmployeeName(String desiredName) {
        Query query = getSession()
                .createSQLQuery("SELECT a.* FROM find_employee_by_name('" + desiredName + "') as a")
                .addEntity("a", EmployeeView.class);
        Collection<EmployeeView> c = query.list();
        return query.list();
    }
}
