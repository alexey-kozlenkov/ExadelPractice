package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.IFacultyDAO;
import com.exadel.studbase.domain.impl.Faculty;
import com.exadel.studbase.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class FacultyServiceImpl  implements IFacultyService{
    @Autowired
    IFacultyDAO facultyDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Faculty save(Faculty faculty) {
        return facultyDAO.saveOrUpdate(faculty);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Faculty getById(Long id) {
        return facultyDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Faculty faculty) {
        facultyDAO.delete(faculty);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Faculty> getAll() {
        return facultyDAO.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<Faculty> getAllForUniversity(Long universityId) {
        return facultyDAO.getAllForUniversity(universityId);
    }
}
