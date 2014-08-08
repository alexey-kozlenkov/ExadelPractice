package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillSetService {
    SkillSet save(SkillSet skillSet);

    SkillSet getById(Long id);

    void delete(SkillSet skillSet);

    Collection<SkillSet> getAll();

    Collection<User> getAllWithSkill(Long skillTypeId);

    Collection<SkillType> getAllForUser(Long userId);
}
