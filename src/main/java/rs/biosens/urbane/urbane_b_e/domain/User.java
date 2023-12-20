package rs.biosens.urbane.urbane_b_e.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"User\"")
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String role;

    @Column
    private String country;

    @Column
    private String climateZone;

    @Column
    private LocalDate createdAt;

    @OneToMany(mappedBy = "users")
    private Set<UserAnswer> usersUserAnswers;

    @OneToMany(mappedBy = "users")
    private Set<QuizAnswer> usersQuizAnswers;

    @OneToMany(mappedBy = "users")
    private Set<Field> usersFields;

    @OneToMany(mappedBy = "users")
    private Set<QuizResult> usersQuizResults;

}
