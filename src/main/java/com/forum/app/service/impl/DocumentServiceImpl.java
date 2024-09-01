package com.forum.app.service.impl;

import org.springframework.stereotype.Service;

import com.forum.app.entity.Document;
import com.forum.app.repository.DocumentRepository;
import com.forum.app.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	private final DocumentRepository documentRepository;

	public DocumentServiceImpl(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	@Override
	public void deleteDocument(String documentPath) {
		Document document = documentRepository.findByDocumentPath(documentPath);
		documentRepository.delete(document);
	}
}