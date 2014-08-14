package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IFacultyDAO;
import com.exadel.studbase.domain.impl.Faculty;
import com.exadel.studbase.domain.impl.StudentView;
import org.springframework.stereotype.Repository;

@Repository
public class FacultyDAOImpl extends GenericDAOImpl<Faculty, StudentView, Long> implements IFacultyDAO{
}
