package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Solution;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.domain.UserAnswer;


public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

    UserAnswer findFirstByUsers(User user);

    UserAnswer findFirstByAnswers(Answer answer);

    UserAnswer findFirstBySolutions(Solution solution);

}
