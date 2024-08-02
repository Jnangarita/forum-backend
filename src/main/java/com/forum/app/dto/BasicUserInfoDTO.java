package com.forum.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicUserInfoDTO {
	private Long id;
	private String photo;
	private String city;
	private String userName;
	private Integer reputation;
	private List<IdValueDTO> category;

	public BasicUserInfoDTO(Long id, String photo, String city, String userName, Integer reputation,
			List<IdValueDTO> category) {
		this.id = id;
		this.photo = photo;
		this.city = city;
		this.userName = userName;
		this.reputation = reputation;
		this.category = category;
	}
}