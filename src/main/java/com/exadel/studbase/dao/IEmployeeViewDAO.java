package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.EmployeeView;

import java.util.Collection;

public interface IEmployeeViewDAO extends GenericDAO<EmployeeView, EmployeeView, Long> {
    Collection<EmployeeView> getViewByEmployeeName(String desiredName);
}
