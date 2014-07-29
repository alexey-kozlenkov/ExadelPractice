package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillSetService {
    public SkillSet save(SkillSet skillSet);

    public SkillSet getById(Long id);

    public void delete(SkillSet skillSet);

    public Collection<SkillSet> getAll();

    public Collection<User> getAllWithSkill(Long skillTypeId);

    public Collection<SkillType> getAllForUser(Long userId);
}
