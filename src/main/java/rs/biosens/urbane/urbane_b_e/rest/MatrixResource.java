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
import rs.biosens.urbane.urbane_b_e.model.MatrixDTO;
import rs.biosens.urbane.urbane_b_e.service.MatrixService;


@RestController
@RequestMapping(value = "/api/matrices", produces = MediaType.APPLICATION_JSON_VALUE)
public class MatrixResource {

    private final MatrixService matrixService;

    public MatrixResource(final MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    @GetMapping
    public ResponseEntity<List<MatrixDTO>> getAllMatrices() {
        return ResponseEntity.ok(matrixService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatrixDTO> getMatrix(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(matrixService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMatrix(@RequestBody @Valid final MatrixDTO matrixDTO) {
        final Integer createdId = matrixService.create(matrixDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMatrix(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MatrixDTO matrixDTO) {
        matrixService.update(id, matrixDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMatrix(@PathVariable(name = "id") final Integer id) {
        matrixService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
