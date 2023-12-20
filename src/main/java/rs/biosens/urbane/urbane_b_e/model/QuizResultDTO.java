package rs.biosens.urbane.urbane_b_e.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuizResultDTO {

    private Integer id;
    private Integer score;
    private LocalDate created;
    private Integer users;

}
