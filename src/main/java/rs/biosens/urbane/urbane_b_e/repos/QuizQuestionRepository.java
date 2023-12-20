package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.QuizQuestion;


public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {
}
