package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.DAOException;
import com.exadel.studbase.dao.GenericDAO;
import com.exadel.studbase.domain.IEntity;
import com.exadel.studbase.service.filter.Filter;
import com.exadel.studbase.service.filter.FilterUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

public abstract class GenericDAOImpl<CONTENT extends IEntity, VIEW extends IEntity, ID extends Serializable>
        extends HibernateDaoSupport implements GenericDAO<CONTENT, VIEW, ID> {

    private Class<CONTENT> contentClass = (Class<CONTENT>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<VIEW> viewClass = (Class<VIEW>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    @Qualifier("sessionFactory")
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Collection<CONTENT> getAll() throws DAOException {
        try {
            Collection<CONTENT> result =
                    getSession().createCriteria(contentClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            return result;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public CONTENT find(ID id) {
        try {
            CONTENT content = (CONTENT) this.getSession().get(contentClass, id);
            return content;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public CONTENT saveOrUpdate(CONTENT content) {
        try {
            this.getSession().saveOrUpdate(content);
            return content;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(CONTENT content) {
        try {
            this.getSession().delete(content);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Collection<VIEW> getView(Map<String, Filter<VIEW>> filterMap) {
        try {
            Criteria listCriteria = getSession().createCriteria(viewClass);
            Criterion filterCriterion = FilterUtils.buildFilterCriterion(filterMap);
            listCriteria.add(filterCriterion);
            Collection<VIEW> result = listCriteria.list();
            return result;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
