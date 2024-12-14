package com.forum.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "ciudades")
public class City implements Serializable {
	private static final long serialVersionUID = 3L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_ciudad", nullable = false, length = 100)
	private String cityName;

	@ManyToOne
	@JoinColumn(name = "pais_id", nullable = false)
	@JsonBackReference
	private Country country;
}