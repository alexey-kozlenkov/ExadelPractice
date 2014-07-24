package com.exadel.studbase.service;

import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.user.User;

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
}
