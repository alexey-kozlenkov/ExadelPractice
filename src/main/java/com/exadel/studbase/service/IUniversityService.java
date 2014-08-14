package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.University;
import java.util.Collection;

public interface IUniversityService {
    University save(University university);

    University getById(Long id);

    void delete(University university);

    Collection<University> getAll();
}
