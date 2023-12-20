package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.QuizAnswer;
import rs.biosens.urbane.urbane_b_e.domain.QuizQuestion;
import rs.biosens.urbane.urbane_b_e.domain.User;


public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Integer> {

    QuizAnswer findFirstByQuizQuestions(QuizQuestion quizQuestion);

    QuizAnswer findFirstByUsers(User user);

}
