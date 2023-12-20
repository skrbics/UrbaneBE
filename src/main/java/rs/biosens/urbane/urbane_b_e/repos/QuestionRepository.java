package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.Question;
import rs.biosens.urbane.urbane_b_e.domain.QuestionCategory;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findFirstByQuestionCategories(QuestionCategory questionCategory);

}
