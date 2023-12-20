package rs.biosens.urbane.urbane_b_e.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.biosens.urbane.urbane_b_e.domain.Field;
import rs.biosens.urbane.urbane_b_e.domain.User;


public interface FieldRepository extends JpaRepository<Field, Integer> {

    Field findFirstByUsers(User user);

}
