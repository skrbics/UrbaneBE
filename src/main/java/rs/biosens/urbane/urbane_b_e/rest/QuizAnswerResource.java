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
import rs.biosens.urbane.urbane_b_e.model.QuizAnswerDTO;
import rs.biosens.urbane.urbane_b_e.service.QuizAnswerService;


@RestController
@RequestMapping(value = "/api/quizAnswers", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizAnswerResource {

    private final QuizAnswerService quizAnswerService;

    public QuizAnswerResource(final QuizAnswerService quizAnswerService) {
        this.quizAnswerService = quizAnswerService;
    }

    @GetMapping
    public ResponseEntity<List<QuizAnswerDTO>> getAllQuizAnswers() {
        return ResponseEntity.ok(quizAnswerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizAnswerDTO> getQuizAnswer(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(quizAnswerService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createQuizAnswer(
            @RequestBody @Valid final QuizAnswerDTO quizAnswerDTO) {
        final Integer createdId = quizAnswerService.create(quizAnswerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateQuizAnswer(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final QuizAnswerDTO quizAnswerDTO) {
        quizAnswerService.update(id, quizAnswerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuizAnswer(@PathVariable(name = "id") final Integer id) {
        quizAnswerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
