package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Matrix;
import rs.biosens.urbane.urbane_b_e.domain.Question;
import rs.biosens.urbane.urbane_b_e.domain.UserAnswer;
import rs.biosens.urbane.urbane_b_e.model.AnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.MatrixRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuestionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserAnswerRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final MatrixRepository matrixRepository;
    private final UserAnswerRepository userAnswerRepository;

    public AnswerService(final AnswerRepository answerRepository,
            final QuestionRepository questionRepository, final MatrixRepository matrixRepository,
            final UserAnswerRepository userAnswerRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.matrixRepository = matrixRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    public List<AnswerDTO> findAll() {
        final List<Answer> answers = answerRepository.findAll(Sort.by("id"));
        return answers.stream()
                .map(answer -> mapToDTO(answer, new AnswerDTO()))
                .toList();
    }

    public AnswerDTO get(final Integer id) {
        return answerRepository.findById(id)
                .map(answer -> mapToDTO(answer, new AnswerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AnswerDTO answerDTO) {
        final Answer answer = new Answer();
        mapToEntity(answerDTO, answer);
        return answerRepository.save(answer).getId();
    }

    public void update(final Integer id, final AnswerDTO answerDTO) {
        final Answer answer = answerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(answerDTO, answer);
        answerRepository.save(answer);
    }

    public void delete(final Integer id) {
        answerRepository.deleteById(id);
    }

    private AnswerDTO mapToDTO(final Answer answer, final AnswerDTO answerDTO) {
        answerDTO.setId(answer.getId());
        answerDTO.setAnswer(answer.getAnswer());
        answerDTO.setQuestions(answer.getQuestions() == null ? null : answer.getQuestions().getId());
        return answerDTO;
    }

    private Answer mapToEntity(final AnswerDTO answerDTO, final Answer answer) {
        answer.setAnswer(answerDTO.getAnswer());
        final Question questions = answerDTO.getQuestions() == null ? null : questionRepository.findById(answerDTO.getQuestions())
                .orElseThrow(() -> new NotFoundException("questions not found"));
        answer.setQuestions(questions);
        return answer;
    }

    public String getReferencedWarning(final Integer id) {
        final Answer answer = answerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Matrix answersMatrix = matrixRepository.findFirstByAnswers(answer);
        if (answersMatrix != null) {
            return WebUtils.getMessage("answer.matrix.answers.referenced", answersMatrix.getId());
        }
        final UserAnswer answersUserAnswer = userAnswerRepository.findFirstByAnswers(answer);
        if (answersUserAnswer != null) {
            return WebUtils.getMessage("answer.userAnswer.answers.referenced", answersUserAnswer.getId());
        }
        return null;
    }

}
