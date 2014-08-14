package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IUniversityDAO;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.University;
import org.springframework.stereotype.Repository;


@Repository
public class UniversityDAOImpl extends GenericDAOImpl<University, StudentView, Long> implements IUniversityDAO {
}
