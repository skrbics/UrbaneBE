package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.QuizResult;
import rs.biosens.urbane.urbane_b_e.domain.User;


public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {

    QuizResult findFirstByUsers(User user);

}
