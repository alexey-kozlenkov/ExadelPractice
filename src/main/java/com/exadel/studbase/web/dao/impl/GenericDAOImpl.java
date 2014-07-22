package com.exadel.studbase.web.dao.impl;

import com.exadel.studbase.web.dao.GenericDAO;
import com.exadel.studbase.web.domain.IEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;


/**
 * Created by Алексей on 18.07.14.
 */

public abstract class GenericDAOImpl<CONTENT extends IEntity, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<CONTENT, ID> {

    public Class<CONTENT> contentClass = (Class<CONTENT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    public void init(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    };

    @Override
    public Collection<CONTENT> getAll() {
         Collection<CONTENT> result =
                 getSession().createCriteria(contentClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return result;
    }

    @Override
    public CONTENT find(ID id) {
        return (CONTENT) this.getSession().get(contentClass, id);
    }

    @Override
    public CONTENT saveOrUpdate(CONTENT content) {
        this.getSession().saveOrUpdate(content);
        return content;
    }

    @Override
    public void delete(CONTENT content) {
        this.getSession().delete(content);
    }
}
