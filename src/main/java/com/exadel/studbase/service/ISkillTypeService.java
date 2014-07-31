package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.SkillType;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillTypeService {
    public SkillType save(SkillType skillType);

    public SkillType getById(Long id);

    public void delete(SkillType skillType);

    public Collection<SkillType> getAll();
}
