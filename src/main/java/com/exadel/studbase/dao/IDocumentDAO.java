package com.exadel.studbase.dao;

import com.exadel.studbase.domain.document.Document;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
public interface IDocumentDAO extends GenericDAO<Document, Long> {
    public Collection<Document> getAllForUser(Long id);
}
