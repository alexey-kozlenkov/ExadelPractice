package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillSetDAO;
import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.user.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
@Repository
public class SkillSetDAO extends GenericDAOImpl<SkillSet, Long> implements ISkillSetDAO{
    @Override
    public Collection<User> getAllWithSkill(Long skillTypeId) {
        Query query = getSession().createQuery("FROM User WHERE id in (SELECT user.id FROM SkillSet WHERE skillType.id="+skillTypeId+")");
        return query.list();
    }
}
