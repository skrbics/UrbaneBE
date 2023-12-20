package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Question;
import rs.biosens.urbane.urbane_b_e.domain.QuestionCategory;
import rs.biosens.urbane.urbane_b_e.model.QuestionCategoryDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuestionCategoryRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuestionRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class QuestionCategoryService {

    private final QuestionCategoryRepository questionCategoryRepository;
    private final QuestionRepository questionRepository;

    public QuestionCategoryService(final QuestionCategoryRepository questionCategoryRepository,
            final QuestionRepository questionRepository) {
        this.questionCategoryRepository = questionCategoryRepository;
        this.questionRepository = questionRepository;
    }

    public List<QuestionCategoryDTO> findAll() {
        final List<QuestionCategory> questionCategories = questionCategoryRepository.findAll(Sort.by("id"));
        return questionCategories.stream()
                .map(questionCategory -> mapToDTO(questionCategory, new QuestionCategoryDTO()))
                .toList();
    }

    public QuestionCategoryDTO get(final Integer id) {
        return questionCategoryRepository.findById(id)
                .map(questionCategory -> mapToDTO(questionCategory, new QuestionCategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final QuestionCategoryDTO questionCategoryDTO) {
        final QuestionCategory questionCategory = new QuestionCategory();
        mapToEntity(questionCategoryDTO, questionCategory);
        return questionCategoryRepository.save(questionCategory).getId();
    }

    public void update(final Integer id, final QuestionCategoryDTO questionCategoryDTO) {
        final QuestionCategory questionCategory = questionCategoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionCategoryDTO, questionCategory);
        questionCategoryRepository.save(questionCategory);
    }

    public void delete(final Integer id) {
        questionCategoryRepository.deleteById(id);
    }

    private QuestionCategoryDTO mapToDTO(final QuestionCategory questionCategory,
            final QuestionCategoryDTO questionCategoryDTO) {
        questionCategoryDTO.setId(questionCategory.getId());
        questionCategoryDTO.setCategory(questionCategory.getCategory());
        questionCategoryDTO.setOrder(questionCategory.getOrder());
        return questionCategoryDTO;
    }

    private QuestionCategory mapToEntity(final QuestionCategoryDTO questionCategoryDTO,
            final QuestionCategory questionCategory) {
        questionCategory.setCategory(questionCategoryDTO.getCategory());
        questionCategory.setOrder(questionCategoryDTO.getOrder());
        return questionCategory;
    }

    public String getReferencedWarning(final Integer id) {
        final QuestionCategory questionCategory = questionCategoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Question questionCategoriesQuestion = questionRepository.findFirstByQuestionCategories(questionCategory);
        if (questionCategoriesQuestion != null) {
            return WebUtils.getMessage("questionCategory.question.questionCategories.referenced", questionCategoriesQuestion.getId());
        }
        return null;
    }

}
