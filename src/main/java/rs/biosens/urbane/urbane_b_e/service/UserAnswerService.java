package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Solution;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.domain.UserAnswer;
import rs.biosens.urbane.urbane_b_e.model.UserAnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.SolutionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserAnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;


@Service
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    public UserAnswerService(final UserAnswerRepository userAnswerRepository,
            final UserRepository userRepository, final AnswerRepository answerRepository,
            final SolutionRepository solutionRepository) {
        this.userAnswerRepository = userAnswerRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.solutionRepository = solutionRepository;
    }

    public List<UserAnswerDTO> findAll() {
        final List<UserAnswer> userAnswers = userAnswerRepository.findAll(Sort.by("id"));
        return userAnswers.stream()
                .map(userAnswer -> mapToDTO(userAnswer, new UserAnswerDTO()))
                .toList();
    }

    public UserAnswerDTO get(final Integer id) {
        return userAnswerRepository.findById(id)
                .map(userAnswer -> mapToDTO(userAnswer, new UserAnswerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserAnswerDTO userAnswerDTO) {
        final UserAnswer userAnswer = new UserAnswer();
        mapToEntity(userAnswerDTO, userAnswer);
        return userAnswerRepository.save(userAnswer).getId();
    }

    public void update(final Integer id, final UserAnswerDTO userAnswerDTO) {
        final UserAnswer userAnswer = userAnswerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userAnswerDTO, userAnswer);
        userAnswerRepository.save(userAnswer);
    }

    public void delete(final Integer id) {
        userAnswerRepository.deleteById(id);
    }

    private UserAnswerDTO mapToDTO(final UserAnswer userAnswer, final UserAnswerDTO userAnswerDTO) {
        userAnswerDTO.setId(userAnswer.getId());
        userAnswerDTO.setUserAnswer(userAnswer.getUserAnswer());
        userAnswerDTO.setCreated(userAnswer.getCreated());
        userAnswerDTO.setUsers(userAnswer.getUsers() == null ? null : userAnswer.getUsers().getId());
        userAnswerDTO.setAnswers(userAnswer.getAnswers() == null ? null : userAnswer.getAnswers().getId());
        userAnswerDTO.setSolutions(userAnswer.getSolutions() == null ? null : userAnswer.getSolutions().getId());
        return userAnswerDTO;
    }

    private UserAnswer mapToEntity(final UserAnswerDTO userAnswerDTO, final UserAnswer userAnswer) {
        userAnswer.setUserAnswer(userAnswerDTO.getUserAnswer());
        userAnswer.setCreated(userAnswerDTO.getCreated());
        final User users = userAnswerDTO.getUsers() == null ? null : userRepository.findById(userAnswerDTO.getUsers())
                .orElseThrow(() -> new NotFoundException("users not found"));
        userAnswer.setUsers(users);
        final Answer answers = userAnswerDTO.getAnswers() == null ? null : answerRepository.findById(userAnswerDTO.getAnswers())
                .orElseThrow(() -> new NotFoundException("answers not found"));
        userAnswer.setAnswers(answers);
        final Solution solutions = userAnswerDTO.getSolutions() == null ? null : solutionRepository.findById(userAnswerDTO.getSolutions())
                .orElseThrow(() -> new NotFoundException("solutions not found"));
        userAnswer.setSolutions(solutions);
        return userAnswer;
    }

}
