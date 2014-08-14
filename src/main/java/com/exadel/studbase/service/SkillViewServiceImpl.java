package com.exadel.studbase.service;

import com.exadel.studbase.dao.ISkillViewDAO;
import com.exadel.studbase.domain.impl.SkillView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SkillViewServiceImpl implements  ISkillViewService{

    @Autowired
    private ISkillViewDAO skillViewDAO;

    @Override
    public Collection<SkillView> getSkillsForUser(Long userId) {
        return skillViewDAO.getSkillsForUser(userId);
    }

    @Override
    public Collection<SkillView> getAll() {
        return skillViewDAO.getAll();
    }

    @Override
    public SkillView getById(Long id) {
        return skillViewDAO.find(id);
    }

    @Override
    public SkillView save(SkillView skillView) {
        return skillViewDAO.saveOrUpdate(skillView);
    }

    @Override
    public void delete(SkillView skillView) {
        skillViewDAO.delete(skillView);
    }
}
