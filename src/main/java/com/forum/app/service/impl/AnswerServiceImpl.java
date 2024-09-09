package com.forum.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.forum.app.dto.AnswerDTO;
import com.forum.app.dto.UpdateAnswerDTO;
import com.forum.app.dto.response.AnswerResponseDTO;
import com.forum.app.entity.Answer;
import com.forum.app.enumeration.AnswerStatus;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.AnswerRepository;
import com.forum.app.service.AnswerService;
import com.forum.app.utils.Utility;

@Service
public class AnswerServiceImpl implements AnswerService {

	private final Utility utility;
	private final AnswerRepository answerRepository;

	public AnswerServiceImpl(Utility utility, AnswerRepository answerRepository) {
		this.utility = utility;
		this.answerRepository = answerRepository;
	}

	@Transactional
	@Override
	public AnswerResponseDTO createAnswer(AnswerDTO payload) {
		try {
			Answer newAnswer = setAnswer(payload);
			Answer answer = answerRepository.save(newAnswer);
			return new AnswerResponseDTO(answer, utility.getMessage("forum.message.info.created.successfully",
					new Object[] { utility.getMessage("forum.message.answer", null) }));
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.saving.answer", null));
		}
	}

	private Answer setAnswer(AnswerDTO payload) {
		Answer newAnswer = new Answer();
		newAnswer.setQuestionId(payload.getQuestionId());
		newAnswer.setAnswerTxt(payload.getAnswerTxt());
		newAnswer.setUserId(payload.getUserId());
		newAnswer.setAnswerStatus(AnswerStatus.SENT.getStatus());
		return newAnswer;
	}

	@Override
	public AnswerResponseDTO getAnswerById(Long id) {
		try {
			Answer answer = findAnswerById(id);
			return new AnswerResponseDTO(answer, "");
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.answer", null));
		}
	}

	@Override
	public Answer findAnswerById(Long id) {
		return answerRepository.getReferenceById(id);
	}

	@Transactional
	@Override
	public AnswerResponseDTO updateAnswer(Long id, UpdateAnswerDTO payload) {
		try {
			Answer updateAnswer = findAnswerById(id);
			updateAnswer.setAnswerTxt(payload.getAnswerTxt());
			Answer answer = answerRepository.save(updateAnswer);
			return new AnswerResponseDTO(answer, utility.getMessage("forum.message.info.updated.successfully",
					new Object[] { utility.getMessage("forum.message.txt.answer", null) }));
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException();
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMostSpecificCause().getMessage());
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.updating.answer", null));
		}
	}

	@Override
	public List<AnswerResponseDTO> getAnswerList() {
		try {
			List<Answer> savedAnswerList = answerRepository.findByDeletedFalse();
			List<AnswerResponseDTO> answerList = new ArrayList<>();
			for (Answer answer : savedAnswerList) {
				AnswerResponseDTO answerDto = new AnswerResponseDTO(answer, "");
				answerList.add(answerDto);
			}
			return answerList;
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.list.answer", null));
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
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.deleting.answer", null));
		}
	}
}