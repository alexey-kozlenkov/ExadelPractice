package com.exadel.studbase.service.impl;

import com.exadel.studbase.dao.ISkillTypeDAO;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.service.ISkillTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class SkillTypeServiceImpl implements ISkillTypeService {

    @Autowired
    private ISkillTypeDAO skillTypeDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillType save(SkillType skillType) {
        return skillTypeDAO.saveOrUpdate(skillType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SkillType getById(Long skillTypeId) {
        return skillTypeDAO.find(skillTypeId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(SkillType skillType) {
        skillTypeDAO.delete(skillType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<SkillType> getAll() {
        return skillTypeDAO.getAll();
    }
}
