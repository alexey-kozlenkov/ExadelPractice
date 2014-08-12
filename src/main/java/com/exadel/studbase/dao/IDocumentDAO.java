package com.exadel.studbase.dao;

import com.exadel.studbase.domain.impl.Document;
import com.exadel.studbase.domain.impl.StudentView;

import java.util.Collection;

public interface IDocumentDAO extends GenericDAO<Document, StudentView, Long> {
    Collection<Document> getActualForUser(Long id);
    Collection<Document> getNotActualForUser(Long id);
}
