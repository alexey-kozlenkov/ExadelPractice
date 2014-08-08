package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.SkillType;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillTypeService {
    SkillType save(SkillType skillType);

    SkillType getById(Long id);

    void delete(SkillType skillType);

    Collection<SkillType> getAll();
}
