package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillSetDAO extends GenericDAO<SkillSet, Long> {
    public Collection<User> getAllWithSkill(Long skillTypeId);

    public Collection<SkillType> getAllForUser(Long userId);
}
