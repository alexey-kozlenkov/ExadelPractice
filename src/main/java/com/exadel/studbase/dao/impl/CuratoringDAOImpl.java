package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class CuratoringDAOImpl extends GenericDAOImpl<Curatoring, StudentView, Long> implements ICuratoringDAO {
    @Override
    public Collection<StudentView> getAllStudentsForEmployee(Long employeeId) {
        Query query = getSession().createQuery(
                "FROM StudentView where id IN(SELECT studentId FROM Curatoring WHERE employeeId =" + employeeId + ")");
        return query.list();
    }

    @Override
    public Collection<Employee> getAllMastersForStudent(Long studentId) {
        Query query = getSession().createQuery(
                "FROM EmployeeView where id IN(SELECT employeeId FROM Curatoring WHERE studentId =" + studentId + ")");
        return query.list();
    }

    @Override
    public void appointCuratorsToStudents(Long[] studentIds, Long[] curatorIds) {
        Query query = getSession().createSQLQuery("" +
                "INSERT INTO \"CURATORING\" (student_id, employee_id) VALUES (:studentIdParam, :curatorIdParam)");
        for (Long studentId: studentIds) {
            for (Long curatorId: curatorIds) {
                query.setParameter("studentIdParam", studentId);
                query.setParameter("curatorIdParam", curatorId);
                query.executeUpdate();
            }
        }
    }
}
