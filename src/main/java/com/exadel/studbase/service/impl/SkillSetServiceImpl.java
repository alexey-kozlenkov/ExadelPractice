package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.ISkillSetDAO;
import com.exadel.studbase.domain.skills.SkillSet;
import com.exadel.studbase.domain.skills.SkillType;
import com.exadel.studbase.domain.user.User;
import com.exadel.studbase.service.ISkillSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Алексей on 24.07.14.
 */
@Service
public class SkillSetServiceImpl implements ISkillSetService {

    @Autowired
    private ISkillSetDAO skillSetDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillSet save(SkillSet skillSet) {
        return skillSetDAO.saveOrUpdate(skillSet);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillSet getById(Long skillSetId) {
        return skillSetDAO.find(skillSetId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(SkillSet skillSet) {
        skillSetDAO.delete(skillSet);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<SkillSet> getAll() {
        return skillSetDAO.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<User> getAllWithSkill(Long skillTypeId) {
        return skillSetDAO.getAllWithSkill(skillTypeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<SkillType> getAllForUser(Long userId) {
        return skillSetDAO.getAllForUser(userId);
    }
}
