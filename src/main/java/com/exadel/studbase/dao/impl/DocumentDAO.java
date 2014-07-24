package com.exadel.studbase.dao.impl;

import com.exadel.studbase.dao.IDocumentDAO;
import com.exadel.studbase.domain.document.Document;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
@Repository
public class DocumentDAO extends GenericDAOImpl<Document, Long> implements IDocumentDAO {
    @Override
    public Collection<Document> getAllForUser(Long id) {
        Query query = getSession().createQuery("FROM Document WHERE student.id="+id);
        return query.list();
    }
}
