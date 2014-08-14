package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;

public interface ISkillSetDAO extends GenericDAO<SkillSet, SkillSet, Long> {
    Collection<User> getAllWithSkill(Long skillTypeId);

    Collection<SkillType> getAllForUser(Long userId);

    void addNewSkillToUser(Long userId, Long[] skillTypeIds, Long[] levels);

    void deleteAllSkillsForUser(Long userId);
}
