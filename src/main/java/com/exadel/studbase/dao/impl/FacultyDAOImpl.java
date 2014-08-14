package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IFacultyDAO;
import com.exadel.studbase.domain.impl.Faculty;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class FacultyDAOImpl extends GenericDAOImpl<Faculty, StudentView, Long> implements IFacultyDAO{

    @Override
    public Collection<Faculty> getAllForUniversity(Long universityId) {
        Query query = getSession().createQuery("FROM Faculty where university.id=:universityId");
        query.setParameter("universityId", universityId);
        return query.list();
    }
}
