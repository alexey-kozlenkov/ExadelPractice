package com.exadel.studbase.service;

import com.exadel.studbase.domain.impl.Document;

import java.util.Collection;

public interface IDocumentService {

    Document save(Document document);

    Document getById(Long id);

    void delete(Document document);

    Collection<Document> getActualForUser(Long id);

    Collection<Document> getNotActualForUser(Long id);
}
