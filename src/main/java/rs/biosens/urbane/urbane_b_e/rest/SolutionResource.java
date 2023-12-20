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
import rs.biosens.urbane.urbane_b_e.model.SolutionDTO;
import rs.biosens.urbane.urbane_b_e.service.SolutionService;


@RestController
@RequestMapping(value = "/api/solutions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolutionResource {

    private final SolutionService solutionService;

    public SolutionResource(final SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @GetMapping
    public ResponseEntity<List<SolutionDTO>> getAllSolutions() {
        return ResponseEntity.ok(solutionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> getSolution(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(solutionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createSolution(
            @RequestBody @Valid final SolutionDTO solutionDTO) {
        final Integer createdId = solutionService.create(solutionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateSolution(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final SolutionDTO solutionDTO) {
        solutionService.update(id, solutionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSolution(@PathVariable(name = "id") final Integer id) {
        solutionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
