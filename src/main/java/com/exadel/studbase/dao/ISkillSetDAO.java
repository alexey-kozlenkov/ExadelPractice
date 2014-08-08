package com.exadel.studbase.dao;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.impl.SkillSet;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.StudentView;
import com.exadel.studbase.domain.impl.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 24.07.14.
 */
public interface ISkillSetDAO extends GenericDAO<SkillSet, SkillSet, Long> {
    public Collection<User> getAllWithSkill(Long skillTypeId);

    public Collection<SkillType> getAllForUser(Long userId);
}
