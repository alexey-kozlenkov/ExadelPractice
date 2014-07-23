package com.exadel.studbase.web.dao;

import com.exadel.studbase.web.domain.document.Document;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
public interface IDocumentDAO extends GenericDAO<Document, Long> {

    Collection<Document> getAllForUser(Long id);
}
