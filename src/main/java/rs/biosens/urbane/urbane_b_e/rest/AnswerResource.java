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
import rs.biosens.urbane.urbane_b_e.model.AnswerDTO;
import rs.biosens.urbane.urbane_b_e.service.AnswerService;


@RestController
@RequestMapping(value = "/api/answers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnswerResource {

    private final AnswerService answerService;

    public AnswerResource(final AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<AnswerDTO>> getAllAnswers() {
        return ResponseEntity.ok(answerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(answerService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createAnswer(@RequestBody @Valid final AnswerDTO answerDTO) {
        final Integer createdId = answerService.create(answerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAnswer(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final AnswerDTO answerDTO) {
        answerService.update(id, answerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAnswer(@PathVariable(name = "id") final Integer id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
