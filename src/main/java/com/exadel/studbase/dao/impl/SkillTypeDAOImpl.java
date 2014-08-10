package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillTypeDAO;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.StudentView;
import org.springframework.stereotype.Repository;

@Repository
public class SkillTypeDAOImpl extends GenericDAOImpl<SkillType, StudentView, Long> implements ISkillTypeDAO {
}
