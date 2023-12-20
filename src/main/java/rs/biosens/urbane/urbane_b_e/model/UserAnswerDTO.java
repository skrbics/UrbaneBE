package rs.biosens.urbane.urbane_b_e.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAnswerDTO {

    private Integer id;

    @Size(max = 255)
    private String userAnswer;

    private LocalDate created;

    private Integer users;

    private Integer answers;

    private Integer solutions;

}
