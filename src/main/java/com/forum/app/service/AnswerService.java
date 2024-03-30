package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;
import com.forum.app.dto.UpdateAnswerDTO;
import com.forum.app.entity.Answer;

public interface AnswerService {
	AnswerResponseDTO createAnswer(AnswerDTO payload);

	AnswerResponseDTO getAnswerById(Long id);

	Answer findAnswerById(Long id);

	AnswerResponseDTO updateAnswer(UpdateAnswerDTO payload);

	List<AnswerResponseDTO> getAnswerList();

	void deleteAnswer(Long id);
}