package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Matrix;
import rs.biosens.urbane.urbane_b_e.domain.Solution;


public interface MatrixRepository extends JpaRepository<Matrix, Integer> {

    Matrix findFirstByAnswers(Answer answer);

    Matrix findFirstBySolutions(Solution solution);

}
