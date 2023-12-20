package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Field;
import rs.biosens.urbane.urbane_b_e.domain.QuizAnswer;
import rs.biosens.urbane.urbane_b_e.domain.QuizResult;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.domain.UserAnswer;
import rs.biosens.urbane.urbane_b_e.model.UserDTO;
import rs.biosens.urbane.urbane_b_e.repos.FieldRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuizAnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuizResultRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserAnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final FieldRepository fieldRepository;
    private final QuizResultRepository quizResultRepository;

    public UserService(final UserRepository userRepository,
            final UserAnswerRepository userAnswerRepository,
            final QuizAnswerRepository quizAnswerRepository, final FieldRepository fieldRepository,
            final QuizResultRepository quizResultRepository) {
        this.userRepository = userRepository;
        this.userAnswerRepository = userAnswerRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.fieldRepository = fieldRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setCountry(user.getCountry());
        userDTO.setClimateZone(user.getClimateZone());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setCountry(userDTO.getCountry());
        user.setClimateZone(userDTO.getClimateZone());
        user.setCreatedAt(userDTO.getCreatedAt());
        return user;
    }

    public String getReferencedWarning(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserAnswer usersUserAnswer = userAnswerRepository.findFirstByUsers(user);
        if (usersUserAnswer != null) {
            return WebUtils.getMessage("user.userAnswer.users.referenced", usersUserAnswer.getId());
        }
        final QuizAnswer usersQuizAnswer = quizAnswerRepository.findFirstByUsers(user);
        if (usersQuizAnswer != null) {
            return WebUtils.getMessage("user.quizAnswer.users.referenced", usersQuizAnswer.getId());
        }
        final Field usersField = fieldRepository.findFirstByUsers(user);
        if (usersField != null) {
            return WebUtils.getMessage("user.field.users.referenced", usersField.getId());
        }
        final QuizResult usersQuizResult = quizResultRepository.findFirstByUsers(user);
        if (usersQuizResult != null) {
            return WebUtils.getMessage("user.quizResult.users.referenced", usersQuizResult.getId());
        }
        return null;
    }

}
