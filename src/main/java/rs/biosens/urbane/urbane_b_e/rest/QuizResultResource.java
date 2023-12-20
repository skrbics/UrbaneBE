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
import rs.biosens.urbane.urbane_b_e.model.QuizResultDTO;
import rs.biosens.urbane.urbane_b_e.service.QuizResultService;


@RestController
@RequestMapping(value = "/api/quizResults", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizResultResource {

    private final QuizResultService quizResultService;

    public QuizResultResource(final QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @GetMapping
    public ResponseEntity<List<QuizResultDTO>> getAllQuizResults() {
        return ResponseEntity.ok(quizResultService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResultDTO> getQuizResult(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(quizResultService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createQuizResult(
            @RequestBody @Valid final QuizResultDTO quizResultDTO) {
        final Integer createdId = quizResultService.create(quizResultDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateQuizResult(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final QuizResultDTO quizResultDTO) {
        quizResultService.update(id, quizResultDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuizResult(@PathVariable(name = "id") final Integer id) {
        quizResultService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
