package com.exadel.studbase.web.dao;

import com.exadel.studbase.web.domain.IEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Алексей on 18.07.14.
 */
public interface GenericDAO<CONTENT extends IEntity, ID extends Serializable> {

    public Collection<CONTENT> getAll();

    public CONTENT find (ID id);

    public CONTENT saveOrUpdate (CONTENT content);

    public void delete(CONTENT content);
}
