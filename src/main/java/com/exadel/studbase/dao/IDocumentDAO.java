package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Document;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
public interface IDocumentDAO extends GenericDAO<Document, StudentView, Long> {
    public Collection<Document> getAllForUser(Long id);
}
