package rs.biosens.urbane.urbane_b_e.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuestionDTO {

    private Integer id;

    private String question;

    @Size(max = 255)
    private String type;

    private Integer order;

    private Integer questionCategories;

}
