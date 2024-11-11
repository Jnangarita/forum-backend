package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.request.SaveAnswerInput;
import com.forum.app.dto.request.UpdateAnswerInput;
import com.forum.app.dto.response.AnswerResponseDTO;
import com.forum.app.entity.Answer;

public interface AnswerService {
	AnswerResponseDTO createAnswer(SaveAnswerInput payload);

	AnswerResponseDTO getAnswerById(Long id);

	Answer findAnswerById(Long id);

	AnswerResponseDTO updateAnswer(Long id, UpdateAnswerInput payload);

	List<AnswerResponseDTO> getAnswerList();

	void deleteAnswer(Long id);
}