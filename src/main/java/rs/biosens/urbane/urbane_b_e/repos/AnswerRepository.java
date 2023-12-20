package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Question;


public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Answer findFirstByQuestions(Question question);

}
