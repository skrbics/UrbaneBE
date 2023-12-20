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
import rs.biosens.urbane.urbane_b_e.model.QuestionDTO;
import rs.biosens.urbane.urbane_b_e.service.QuestionService;


@RestController
@RequestMapping(value = "/api/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionResource {

    private final QuestionService questionService;

    public QuestionResource(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(questionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createQuestion(
            @RequestBody @Valid final QuestionDTO questionDTO) {
        final Integer createdId = questionService.create(questionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateQuestion(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final QuestionDTO questionDTO) {
        questionService.update(id, questionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestion(@PathVariable(name = "id") final Integer id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
