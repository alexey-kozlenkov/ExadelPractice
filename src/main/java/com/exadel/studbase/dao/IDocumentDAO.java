package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Document;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
public interface IDocumentDAO extends GenericDAO<Document, Long> {
    public Collection<Document> getActualForUser(Long id);
    public Collection<Document> getNotActualForUser(Long id);
}
