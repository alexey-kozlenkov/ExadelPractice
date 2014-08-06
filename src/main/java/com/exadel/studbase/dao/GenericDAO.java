package com.exadel.studbase.dao;

import com.exadel.studbase.dao.filter.Filter;
import com.exadel.studbase.domain.IEntity;
import com.exadel.studbase.domain.impl.StudentView;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Алексей on 18.07.14.
 */
public interface GenericDAO<CONTENT extends IEntity, ID extends Serializable> {

    public Collection<CONTENT> getAll();

    public CONTENT find(ID id);

    public CONTENT saveOrUpdate(CONTENT content);

    public void delete(CONTENT content);

    public List<StudentView> getView(Map<String, Filter<StudentView>> filterMap);
}
