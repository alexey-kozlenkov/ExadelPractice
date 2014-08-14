package com.exadel.studbase.service.impl;


import com.exadel.studbase.dao.IUniversityDAO;
import com.exadel.studbase.domain.impl.University;
import com.exadel.studbase.service.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UniversityServiceImpl implements IUniversityService{

    @Autowired
    IUniversityDAO universityDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public University save(University university) {
        return universityDAO.saveOrUpdate(university);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public University getById(Long id) {
        return universityDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(University university) {
        universityDAO.delete(university);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<University> getAll() {
        return universityDAO.getAll();
    }
}
