package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class CuratoringDAOImpl extends GenericDAOImpl<Curatoring, StudentView, Long> implements ICuratoringDAO {
    @Override
    public Collection<StudentView> getAllStudentsForEmployee(Long employeeId) {
        Query query = getSession().createQuery(
                "FROM StudentView where id IN(SELECT student.id FROM Curatoring WHERE employee.id =" + employeeId + ")");
        return query.list();
    }

    @Override
    public Collection<Employee> getAllMastersForStudent(Long studentId) {
        Query query = getSession().createQuery(
                "FROM EmployeeView where id IN(SELECT employee.id FROM Curatoring WHERE student.id =" + studentId + ")");
        return query.list();
    }
}
