package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IDocumentDAO;
import com.exadel.studbase.domain.impl.Document;
import com.exadel.studbase.domain.impl.StudentView;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Collection;

@Repository
public class DocumentDAOImpl extends GenericDAOImpl<Document, StudentView, Long> implements IDocumentDAO {
    @Override
    public Collection<Document> getActualForUser(Long id) {
        Query query = getSession().createQuery("FROM Document WHERE studentId=" + id
                + " AND (expirationDate >= \'" + new Date(System.currentTimeMillis()) + "' OR expirationDate IS NULL) ORDER BY expirationDate ASC");
        return query.list();
    }

    @Override
    public Collection<Document> getNotActualForUser(Long id) {
        Query query = getSession().createQuery("FROM Document WHERE studentId=" + id
                + " AND expirationDate < '" + new Date(System.currentTimeMillis()) + "' ORDER BY expirationDate ASC");
        return query.list();
    }
}
