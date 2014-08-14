package com.exadel.studbase.dao;

import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.domain.IEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface GenericDAO<CONTENT extends IEntity,  VIEW extends IEntity, ID extends Serializable> {
    Collection<CONTENT> getAll() throws DAOException;

    CONTENT find(ID id) throws DAOException;

    CONTENT saveOrUpdate(CONTENT content) throws DAOException;

    void delete(CONTENT content)throws DAOException;

    Collection<VIEW> getView(Map<String, Filter<VIEW>> filterMap)throws DAOException;
}
