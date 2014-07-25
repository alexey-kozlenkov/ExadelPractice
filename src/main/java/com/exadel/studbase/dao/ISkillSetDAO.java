package com.exadel.studbase.dao;

import com.exadel.studbase.dao.GenericDAO;
import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.skills.SkillType;
import com.exadel.studbase.domain.user.User;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillSetDAO extends GenericDAO<SkillSet, Long> {
    public Collection<User> getAllWithSkill (Long skillTypeId);
    public Collection<SkillType> getAllForUser (Long userId);
}
