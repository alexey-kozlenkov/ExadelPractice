package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IEmployeeViewDAO;
import com.exadel.studbase.domain.impl.EmployeeView;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeViewDAOImpl extends GenericDAOImpl<EmployeeView, EmployeeView, Long> implements IEmployeeViewDAO {
}
