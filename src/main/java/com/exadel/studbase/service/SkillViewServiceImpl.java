package com.exadel.studbase.service;

import com.exadel.studbase.dao.ISkillViewDAO;
import com.exadel.studbase.domain.impl.SkillView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class SkillViewServiceImpl implements  ISkillViewService{

    @Autowired
    private ISkillViewDAO skillViewDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<SkillView> getSkillsForUser(Long userId) {
        return skillViewDAO.getSkillsForUser(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<SkillView> getAll() {
        return skillViewDAO.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillView getById(Long id) {
        return skillViewDAO.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillView save(SkillView skillView) {
        return skillViewDAO.saveOrUpdate(skillView);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(SkillView skillView) {
        skillViewDAO.delete(skillView);
    }
}
