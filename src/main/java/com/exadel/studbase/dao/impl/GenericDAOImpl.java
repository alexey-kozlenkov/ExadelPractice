package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.GenericDAO;
import com.exadel.studbase.domain.IEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;


/**
 * Created by Алексей on 18.07.14.
 */

public abstract class GenericDAOImpl<CONTENT extends IEntity, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<CONTENT, ID> {

    public Class<CONTENT> contentClass = (Class<CONTENT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @Autowired
    @Qualifier("sessionFactory")
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    ;

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
