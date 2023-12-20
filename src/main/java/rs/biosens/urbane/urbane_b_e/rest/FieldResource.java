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
import rs.biosens.urbane.urbane_b_e.model.FieldDTO;
import rs.biosens.urbane.urbane_b_e.service.FieldService;


@RestController
@RequestMapping(value = "/api/fields", produces = MediaType.APPLICATION_JSON_VALUE)
public class FieldResource {

    private final FieldService fieldService;

    public FieldResource(final FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    public ResponseEntity<List<FieldDTO>> getAllFields() {
        return ResponseEntity.ok(fieldService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDTO> getField(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(fieldService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createField(@RequestBody @Valid final FieldDTO fieldDTO) {
        final Integer createdId = fieldService.create(fieldDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateField(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final FieldDTO fieldDTO) {
        fieldService.update(id, fieldDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteField(@PathVariable(name = "id") final Integer id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
