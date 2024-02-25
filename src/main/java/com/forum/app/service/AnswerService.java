package com.forum.app.service;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;

public interface AnswerService {
	AnswerResponseDTO createAnswer(AnswerDTO payload);

	AnswerResponseDTO getAnswerById(Long id);
}