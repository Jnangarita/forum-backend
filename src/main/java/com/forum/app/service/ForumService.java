package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.TopPostDTO;

public interface ForumService {
	List<TopPostDTO> getTopPost(Long id);
}