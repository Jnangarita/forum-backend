package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.request.AnswerInput;
import com.forum.app.dto.response.AnswerOutput;
import com.forum.app.entity.Answer;

public interface AnswerService {
	AnswerOutput createAnswer(AnswerInput payload);

	AnswerOutput getAnswerById(Long id);

	Answer findAnswerById(Long id);

	AnswerOutput updateAnswer(Long id, AnswerInput payload);

	List<AnswerOutput> getAnswerList();

	void deleteAnswer(Long id);
}