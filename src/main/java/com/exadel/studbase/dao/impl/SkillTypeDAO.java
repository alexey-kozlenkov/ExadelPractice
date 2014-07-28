package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.ISkillTypeDAO;
import com.exadel.studbase.domain.impl.SkillType;
import org.springframework.stereotype.Repository;

/**
 * Created by Алексей on 24.07.14.
 */
@Repository
public class SkillTypeDAO extends GenericDAOImpl<SkillType, Long> implements ISkillTypeDAO {
}
