package com.exadel.studbase.web.service.impl;

import com.exadel.studbase.web.dao.IDocumentDAO;
import com.exadel.studbase.web.domain.document.Document;
import com.exadel.studbase.web.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Алексей on 23.07.14.
 */
@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    private IDocumentDAO documentDAO;

    @Override
    public Document save(Document document) {
        return documentDAO.saveOrUpdate(document);
    }

    @Override
    public Document getById(Long id) {
        return documentDAO.find(id);
    }

    @Override
    public void delete(Document document) {
        documentDAO.delete(document);
    }

    @Override
    public Collection<Document> getAllForUser(Long id) {
        return documentDAO.getAllForUser(id);
    }
}
