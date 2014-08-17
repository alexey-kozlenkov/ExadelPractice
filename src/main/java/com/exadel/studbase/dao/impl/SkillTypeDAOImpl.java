package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillTypeDAO;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SkillTypeDAOImpl extends GenericDAOImpl<SkillType, StudentView, Long> implements ISkillTypeDAO {
    @Override
    public Collection<SkillType> getAllSorted() {
        Query query = getSession().createQuery("FROM SkillType ORDER BY name ASC");
        return query.list();
    }
}
