package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillViewDAO;
import com.exadel.studbase.domain.impl.SkillView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SkillViewDAOImpl extends GenericDAOImpl<SkillView, SkillView, Long> implements ISkillViewDAO {
    @Override
    public Collection<SkillView> getSkillsForUser(Long userId) {
        Query query = getSession().createQuery("FROM SkillView WHERE userId=:userId");
        query.setParameter("userId",userId);
        return query.list();
    }
}
