package com.forum.app.dto;

import java.util.List;

import com.forum.app.dto.request.IdValueInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicUserInfoDTO {
	private Long id;
	private String photo;
	private String city;
	private String userName;
	private Integer reputation;
	private List<IdValueInput> category;
}