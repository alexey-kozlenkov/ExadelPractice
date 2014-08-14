package com.exadel.studbase.dao;


import com.exadel.studbase.domain.impl.SkillView;

import java.util.Collection;

public interface ISkillViewDAO extends GenericDAO<SkillView, SkillView, Long>  {
    Collection<SkillView> getSkillsForUser(Long userId);
}
