package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.ICuratoringDAO;
import com.exadel.studbase.domain.impl.Curatoring;
import com.exadel.studbase.domain.impl.Employee;
import com.exadel.studbase.domain.impl.Student;
import com.exadel.studbase.service.ICuratoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Алексей on 03.08.2014.
 */
@Service
public class CuratoringServiceImpl implements ICuratoringService {

    @Autowired
    private ICuratoringDAO curatoringDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Curatoring save(Curatoring curatoring) {
        return curatoringDAO.saveOrUpdate(curatoring);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Curatoring curatoring) {
        curatoringDAO.delete(curatoring);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Curatoring> getAll() {
        return curatoringDAO.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Student> getAllStudentsForEmployee(Long employeeId) {
        return curatoringDAO.getAllStudentsForEmployee(employeeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Employee> getAllMastersForStudent(Long studentId) {
        return curatoringDAO.getAllMastersForStudent(studentId);
    }
}
