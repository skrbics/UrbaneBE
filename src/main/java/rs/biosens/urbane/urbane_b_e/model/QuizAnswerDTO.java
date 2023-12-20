package rs.biosens.urbane.urbane_b_e.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuizAnswerDTO {

    private Integer id;
    private Boolean answer;
    private Integer score;
    private Integer quizQuestions;
    private Integer users;

}
