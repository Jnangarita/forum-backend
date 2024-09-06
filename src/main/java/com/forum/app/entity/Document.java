package com.forum.app.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "documento")
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_documento")
	private Integer idDocument;

	@Column(name = "id_usuario")
	private Long userId;

	@Column(name = "tipo_documento")
	private String documentType;

	@Column(name = "nombre_documento", nullable = false)
	private String documentName;

	@Column(name = "ruta_documento", nullable = false)
	private String documentPath;

	@Column(name = "fecha_creacion")
	private LocalDateTime creationDate;
}