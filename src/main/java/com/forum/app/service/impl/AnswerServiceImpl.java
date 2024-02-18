package com.forum.app.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;
import com.forum.app.entity.Answer;
import com.forum.app.enumeration.AnswerStatus;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	private AnswerRepository answerRepository;

	@Transactional
	@Override
	public AnswerResponseDTO createAnswer(AnswerDTO payload) {
		LocalDateTime currentDate = LocalDateTime.now();
		Answer newAnswer = new Answer();
		newAnswer.setIdQuestion(payload.getIdQuestion());
		newAnswer.setAnswerTxt(payload.getAnswerTxt());
		newAnswer.setIdUser(payload.getIdUser());
		newAnswer.setAnswerStatus(AnswerStatus.SENT.getStatus());
		newAnswer.setCreationDate(currentDate);
		newAnswer.setDeleted(false);
		Answer answer = answerRepository.save(newAnswer);
		return new AnswerResponseDTO(answer);
	}
}