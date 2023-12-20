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
import rs.biosens.urbane.urbane_b_e.model.QuizQuestionDTO;
import rs.biosens.urbane.urbane_b_e.service.QuizQuestionService;


@RestController
@RequestMapping(value = "/api/quizQuestions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizQuestionResource {

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionResource(final QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping
    public ResponseEntity<List<QuizQuestionDTO>> getAllQuizQuestions() {
        return ResponseEntity.ok(quizQuestionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizQuestionDTO> getQuizQuestion(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(quizQuestionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createQuizQuestion(
            @RequestBody @Valid final QuizQuestionDTO quizQuestionDTO) {
        final Integer createdId = quizQuestionService.create(quizQuestionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateQuizQuestion(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final QuizQuestionDTO quizQuestionDTO) {
        quizQuestionService.update(id, quizQuestionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuizQuestion(@PathVariable(name = "id") final Integer id) {
        quizQuestionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
