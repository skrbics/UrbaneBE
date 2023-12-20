package rs.biosens.urbane.urbane_b_e.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.biosens.urbane.urbane_b_e.model.QuestionCategoryDTO;
import rs.biosens.urbane.urbane_b_e.service.QuestionCategoryService;


@RestController
@RequestMapping(value = "/api/questionCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionCategoryResource {

    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryResource(final QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionCategoryDTO>> getAllQuestionCategories() {
        return ResponseEntity.ok(questionCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionCategoryDTO> getQuestionCategory(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(questionCategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createQuestionCategory(
            @RequestBody @Valid final QuestionCategoryDTO questionCategoryDTO) {
        final Integer createdId = questionCategoryService.create(questionCategoryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateQuestionCategory(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final QuestionCategoryDTO questionCategoryDTO) {
        questionCategoryService.update(id, questionCategoryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestionCategory(
            @PathVariable(name = "id") final Integer id) {
        questionCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
