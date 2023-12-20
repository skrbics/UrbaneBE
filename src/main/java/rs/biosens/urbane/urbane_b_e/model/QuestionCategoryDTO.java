package rs.biosens.urbane.urbane_b_e.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionCategoryDTO {

    private Integer id;

    @Size(max = 255)
    private String category;

    private Integer order;

}
