package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

public interface ISkillTypeDAO extends GenericDAO<SkillType, StudentView, Long> {
    Collection<SkillType> getAllSorted();
}
