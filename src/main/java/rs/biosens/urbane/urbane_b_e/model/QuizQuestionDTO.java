package rs.biosens.urbane.urbane_b_e.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuizQuestionDTO {

    private Integer id;
    private String questionEn;
    private String questionFr;
    private Integer order;

}
