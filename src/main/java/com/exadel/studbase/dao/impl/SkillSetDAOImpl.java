package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillSetDAO;
import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SkillSetDAOImpl extends GenericDAOImpl<SkillSet, SkillSet, Long> implements ISkillSetDAO {
    @Override
    public Collection<User> getAllWithSkill(Long skillTypeId) {
        Query query = getSession().createQuery("FROM User WHERE id in (SELECT User.id FROM SkillSet WHERE skillType.id=" + skillTypeId + ")");
        return query.list();
    }

    @Override
    public Collection<SkillType> getAllForUser(Long userId) {
        Query query = getSession().createQuery("FROM SkillType WHERE id IN (SELECT SkillType.id FROM SkillSet WHERE user.id=" + userId + ")");
        return query.list();
    }
}
