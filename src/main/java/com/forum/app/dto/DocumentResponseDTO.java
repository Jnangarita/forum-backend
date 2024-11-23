package com.forum.app.dto;

import java.time.LocalDateTime;

import com.forum.app.entity.Document;

import lombok.Data;

@Data
public class DocumentResponseDTO {
	private Integer idDocument;
	private Long userId;
	private String documentType;
	private String documentName;
	private String documentPath;
	private LocalDateTime creationDate;

	public DocumentResponseDTO(Document document) {
		this.idDocument = document.getIdDocument();
		//this.userId = document.getUserId(); TODO: validar id
		this.documentType = document.getDocumentType();
		this.documentName = document.getDocumentName();
		this.documentPath = document.getDocumentPath();
		this.creationDate = document.getCreationDate();
	}
}