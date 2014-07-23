package com.exadel.studbase.web.service;

import com.exadel.studbase.web.domain.document.Document;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
public interface IDocumentService {

    Document save(Document document);

    Document getById(Long id);

    void delete(Document document);

    Collection<Document> getAllForUser(Long id);
}
