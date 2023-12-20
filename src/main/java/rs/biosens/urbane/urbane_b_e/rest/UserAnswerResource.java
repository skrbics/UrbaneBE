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
import rs.biosens.urbane.urbane_b_e.model.UserAnswerDTO;
import rs.biosens.urbane.urbane_b_e.service.UserAnswerService;


@RestController
@RequestMapping(value = "/api/userAnswers", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAnswerResource {

    private final UserAnswerService userAnswerService;

    public UserAnswerResource(final UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @GetMapping
    public ResponseEntity<List<UserAnswerDTO>> getAllUserAnswers() {
        return ResponseEntity.ok(userAnswerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAnswerDTO> getUserAnswer(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(userAnswerService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createUserAnswer(
            @RequestBody @Valid final UserAnswerDTO userAnswerDTO) {
        final Integer createdId = userAnswerService.create(userAnswerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateUserAnswer(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final UserAnswerDTO userAnswerDTO) {
        userAnswerService.update(id, userAnswerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserAnswer(@PathVariable(name = "id") final Integer id) {
        userAnswerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
