package com.forum.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "paises")
public class Country implements Serializable {
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_pais", nullable = false, length = 50)
	private String countryName;

	@Column(name = "codigo_pais", nullable = false, length = 3)
	private String countryCode;

	@OneToMany(mappedBy = "countryId", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<City> cities;
}