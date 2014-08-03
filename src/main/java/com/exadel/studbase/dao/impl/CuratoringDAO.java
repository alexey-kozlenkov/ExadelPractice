package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
@Repository
public class CuratoringDAO extends GenericDAOImpl<Curatoring, Long> implements ICuratoringDAO {
    @Override
    public Collection<Student> getAllStudentsForEmployee(Long employeeId) {
        Query query = getSession().createQuery(
                "FROM Student where id IN(SELECT studentId FROM Curatoring WHERE employeeId="+employeeId+")");
        return query.list();
    }

    @Override
    public Collection<Employee> getAllMastersForStudent(Long studentId) {
        Query query = getSession().createQuery(
                "FROM Employee where id IN(SELECT employeeId FROM Curatoring WHERE studentId="+studentId+")");
        return query.list();
    }
}
