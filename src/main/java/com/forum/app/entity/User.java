package com.forum.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "primer_nombre", unique = true, nullable = false, length = 50)
	private String firstName;

	@Column(name = "apellido", unique = true, nullable = false, length = 50)
	private String lastName;

	@Column(name = "nombre_usuario", unique = true, nullable = false, length = 100)
	private String fullName;

	@Column(name = "codigo", nullable = false, length = 10)
	private String code;

	@Column(name = "correo_electronico", unique = true, nullable = false, length = 50)
	private String email;

	@Column(name = "contrasena", nullable = false, length = 300)
	private String password;

	@Column(name = "id_pais", unique = true, nullable = false, length = 50)
	private Long countryId;

	@Column(name = "id_ciudad", unique = true, nullable = false, length = 50)
	private Long cityId;

	@Column(name = "numero_preguntas")
	private Integer numberQuestions;

	@Column(name = "numero_respuestas")
	private Integer numberResponses;

	@Column(name = "id_rol", nullable = false)
	private Integer role;

	@Column(name = "fecha_creacion")
	private LocalDateTime createdAt;

	@Column(name = "fecha_modificacion")
	private LocalDateTime updatedAt;

	@Column(name = "eliminado")
	private boolean deleted;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}