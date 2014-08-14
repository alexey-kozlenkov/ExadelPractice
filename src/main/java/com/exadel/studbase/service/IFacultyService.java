package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Faculty;

import java.util.Collection;


public interface IFacultyService {
    Faculty save(Faculty faculty);

    Faculty getById(Long id);

    void delete(Faculty faculty);

    Collection<Faculty> getAll();
}
