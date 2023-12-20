package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.QuizAnswer;
import rs.biosens.urbane.urbane_b_e.domain.QuizQuestion;
import rs.biosens.urbane.urbane_b_e.model.QuizQuestionDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuizAnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuizQuestionRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class QuizQuestionService {

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    public QuizQuestionService(final QuizQuestionRepository quizQuestionRepository,
            final QuizAnswerRepository quizAnswerRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizAnswerRepository = quizAnswerRepository;
    }

    public List<QuizQuestionDTO> findAll() {
        final List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll(Sort.by("id"));
        return quizQuestions.stream()
                .map(quizQuestion -> mapToDTO(quizQuestion, new QuizQuestionDTO()))
                .toList();
    }

    public QuizQuestionDTO get(final Integer id) {
        return quizQuestionRepository.findById(id)
                .map(quizQuestion -> mapToDTO(quizQuestion, new QuizQuestionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final QuizQuestionDTO quizQuestionDTO) {
        final QuizQuestion quizQuestion = new QuizQuestion();
        mapToEntity(quizQuestionDTO, quizQuestion);
        return quizQuestionRepository.save(quizQuestion).getId();
    }

    public void update(final Integer id, final QuizQuestionDTO quizQuestionDTO) {
        final QuizQuestion quizQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(quizQuestionDTO, quizQuestion);
        quizQuestionRepository.save(quizQuestion);
    }

    public void delete(final Integer id) {
        quizQuestionRepository.deleteById(id);
    }

    private QuizQuestionDTO mapToDTO(final QuizQuestion quizQuestion,
            final QuizQuestionDTO quizQuestionDTO) {
        quizQuestionDTO.setId(quizQuestion.getId());
        quizQuestionDTO.setQuestionEn(quizQuestion.getQuestionEn());
        quizQuestionDTO.setQuestionFr(quizQuestion.getQuestionFr());
        quizQuestionDTO.setOrder(quizQuestion.getOrder());
        return quizQuestionDTO;
    }

    private QuizQuestion mapToEntity(final QuizQuestionDTO quizQuestionDTO,
            final QuizQuestion quizQuestion) {
        quizQuestion.setQuestionEn(quizQuestionDTO.getQuestionEn());
        quizQuestion.setQuestionFr(quizQuestionDTO.getQuestionFr());
        quizQuestion.setOrder(quizQuestionDTO.getOrder());
        return quizQuestion;
    }

    public String getReferencedWarning(final Integer id) {
        final QuizQuestion quizQuestion = quizQuestionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final QuizAnswer quizQuestionsQuizAnswer = quizAnswerRepository.findFirstByQuizQuestions(quizQuestion);
        if (quizQuestionsQuizAnswer != null) {
            return WebUtils.getMessage("quizQuestion.quizAnswer.quizQuestions.referenced", quizQuestionsQuizAnswer.getId());
        }
        return null;
    }

}
