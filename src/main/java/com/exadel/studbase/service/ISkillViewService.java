package com.exadel.studbase.service;


import com.exadel.studbase.domain.impl.SkillView;

import java.util.Collection;

public interface ISkillViewService {
    Collection<SkillView> getSkillsForUser(Long userId);

    Collection<SkillView> getAll();

    SkillView getById(Long id);

    SkillView save(SkillView skillView);

    void delete(SkillView skillView);
}
