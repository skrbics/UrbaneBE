package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Question;
import rs.biosens.urbane.urbane_b_e.domain.QuestionCategory;
import rs.biosens.urbane.urbane_b_e.model.QuestionDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuestionCategoryRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuestionRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(final QuestionRepository questionRepository,
            final QuestionCategoryRepository questionCategoryRepository,
            final AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.questionCategoryRepository = questionCategoryRepository;
        this.answerRepository = answerRepository;
    }

    public List<QuestionDTO> findAll() {
        final List<Question> questions = questionRepository.findAll(Sort.by("id"));
        return questions.stream()
                .map(question -> mapToDTO(question, new QuestionDTO()))
                .toList();
    }

    public QuestionDTO get(final Integer id) {
        return questionRepository.findById(id)
                .map(question -> mapToDTO(question, new QuestionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final QuestionDTO questionDTO) {
        final Question question = new Question();
        mapToEntity(questionDTO, question);
        return questionRepository.save(question).getId();
    }

    public void update(final Integer id, final QuestionDTO questionDTO) {
        final Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionDTO, question);
        questionRepository.save(question);
    }

    public void delete(final Integer id) {
        questionRepository.deleteById(id);
    }

    private QuestionDTO mapToDTO(final Question question, final QuestionDTO questionDTO) {
        questionDTO.setId(question.getId());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setType(question.getType());
        questionDTO.setOrder(question.getOrder());
        questionDTO.setQuestionCategories(question.getQuestionCategories() == null ? null : question.getQuestionCategories().getId());
        return questionDTO;
    }

    private Question mapToEntity(final QuestionDTO questionDTO, final Question question) {
        question.setQuestion(questionDTO.getQuestion());
        question.setType(questionDTO.getType());
        question.setOrder(questionDTO.getOrder());
        final QuestionCategory questionCategories = questionDTO.getQuestionCategories() == null ? null : questionCategoryRepository.findById(questionDTO.getQuestionCategories())
                .orElseThrow(() -> new NotFoundException("questionCategories not found"));
        question.setQuestionCategories(questionCategories);
        return question;
    }

    public String getReferencedWarning(final Integer id) {
        final Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Answer questionsAnswer = answerRepository.findFirstByQuestions(question);
        if (questionsAnswer != null) {
            return WebUtils.getMessage("question.answer.questions.referenced", questionsAnswer.getId());
        }
        return null;
    }

}
