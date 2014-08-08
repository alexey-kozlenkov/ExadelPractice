package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillTypeDAO;
import com.exadel.studbase.domain.impl.SkillType;
import com.exadel.studbase.domain.impl.StudentView;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 24.07.14.
 */
@Repository
public class SkillTypeDAO extends GenericDAOImpl<SkillType, StudentView, Long> implements ISkillTypeDAO {
}
