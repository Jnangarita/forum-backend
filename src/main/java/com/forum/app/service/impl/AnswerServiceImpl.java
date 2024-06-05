package com.forum.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.AnswerResponseDTO;
import com.forum.app.dto.UpdateAnswerDTO;
import com.forum.app.entity.Answer;
import com.forum.app.enumeration.AnswerStatus;
import com.forum.app.enumeration.GeneralEnum;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.service.AnswerService;
import com.forum.app.utils.Utility;

@Service
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	private Utility utility;

	@Autowired
	private AnswerRepository answerRepository;

	String generalErrorMessage = GeneralEnum.GENERAL_ERROR_MESSAGE.getMessageKey();

	@Transactional
	@Override
	public AnswerResponseDTO createAnswer(AnswerDTO payload) {
		try {
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
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage(generalErrorMessage, null));
		}
	}

	@Override
	public AnswerResponseDTO getAnswerById(Long id) {
		try {
			Answer answer = findAnswerById(id);
			return new AnswerResponseDTO(answer);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage(generalErrorMessage, null));
		}
	}

	@Override
	public Answer findAnswerById(Long id) {
		return answerRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public AnswerResponseDTO updateAnswer(UpdateAnswerDTO payload) {
		try {
			LocalDateTime currenDate = LocalDateTime.now();
			Answer updateAnswer = findAnswerById(payload.getIdAnswer());
			updateAnswer.setAnswerTxt(payload.getAnswerTxt());
			updateAnswer.setModificationDate(currenDate);
			Answer answer = answerRepository.save(updateAnswer);
			return new AnswerResponseDTO(answer);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage(generalErrorMessage, null));
		}
	}

	@Override
	public List<AnswerResponseDTO> getAnswerList() {
		try {
			List<Answer> savedAnswerList = answerRepository.findByDeletedFalse();
			List<AnswerResponseDTO> answerList = new ArrayList<>();
			for (Answer answer : savedAnswerList) {
				AnswerResponseDTO answerDto = new AnswerResponseDTO(answer);
				answerList.add(answerDto);
			}
			return answerList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage(generalErrorMessage, null));
		}
	}

	@Transactional
	@Override
	public void deleteAnswer(Long id) {
		try {
			Answer answer = findAnswerById(id);
			if (!answer.isDeleted()) {
				answer.setDeleted(true);
			}
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage(generalErrorMessage, null));
		}
	}
}