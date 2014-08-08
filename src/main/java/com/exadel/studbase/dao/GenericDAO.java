package com.exadel.studbase.dao;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.IEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Алексей on 18.07.14.
 */
public interface GenericDAO<CONTENT extends IEntity,  VIEW extends IEntity, ID extends Serializable> {
    Collection<CONTENT> getAll();

    CONTENT find(ID id);

    CONTENT saveOrUpdate(CONTENT content);

    void delete(CONTENT content);

    Collection<VIEW> getView(Map<String, Filter<VIEW>> filterMap);
}
