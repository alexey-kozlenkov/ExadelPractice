package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.service.ICuratoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
@Service
public class CuratoringServiceImpl implements ICuratoringService {

    @Autowired
    private ICuratoringDAO curatoringDAO;

    @Override
    public Curatoring save(Curatoring curatoring) {
        return curatoringDAO.saveOrUpdate(curatoring);
    }

    @Override
    public void delete(Curatoring curatoring) {
        curatoringDAO.delete(curatoring);
    }

    @Override
    public Collection<Curatoring> getAll() {
        return curatoringDAO.getAll();
    }

    @Override
    public Collection<Student> getAllStudentsForEmployee(Long employeeId) {
        return curatoringDAO.getAllStudentsForEmployee(employeeId);
    }

    @Override
    public Collection<Employee> getAllMastersForStudent(Long studentId) {
        return curatoringDAO.getAllMastersForStudent(studentId);
    }
}
