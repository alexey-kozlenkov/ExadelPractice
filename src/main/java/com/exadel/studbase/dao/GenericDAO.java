package com.exadel.studbase.dao;

import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.domain.IEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface GenericDAO<CONTENT extends IEntity,  VIEW extends IEntity, ID extends Serializable> {

    public Collection<CONTENT> getAll();

    public CONTENT find(ID id);

    public CONTENT saveOrUpdate(CONTENT content);

    public void delete(CONTENT content);

    public Collection<VIEW> getView(Map<String, Filter<VIEW>> filterMap);
}
