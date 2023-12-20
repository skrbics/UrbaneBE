package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.QuizAnswer;
import rs.biosens.urbane.urbane_b_e.domain.QuizQuestion;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.QuizAnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuizAnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.QuizQuestionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;


@Service
public class QuizAnswerService {

    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;

    public QuizAnswerService(final QuizAnswerRepository quizAnswerRepository,
            final QuizQuestionRepository quizQuestionRepository,
            final UserRepository userRepository) {
        this.quizAnswerRepository = quizAnswerRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.userRepository = userRepository;
    }

    public List<QuizAnswerDTO> findAll() {
        final List<QuizAnswer> quizAnswers = quizAnswerRepository.findAll(Sort.by("id"));
        return quizAnswers.stream()
                .map(quizAnswer -> mapToDTO(quizAnswer, new QuizAnswerDTO()))
                .toList();
    }

    public QuizAnswerDTO get(final Integer id) {
        return quizAnswerRepository.findById(id)
                .map(quizAnswer -> mapToDTO(quizAnswer, new QuizAnswerDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final QuizAnswerDTO quizAnswerDTO) {
        final QuizAnswer quizAnswer = new QuizAnswer();
        mapToEntity(quizAnswerDTO, quizAnswer);
        return quizAnswerRepository.save(quizAnswer).getId();
    }

    public void update(final Integer id, final QuizAnswerDTO quizAnswerDTO) {
        final QuizAnswer quizAnswer = quizAnswerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(quizAnswerDTO, quizAnswer);
        quizAnswerRepository.save(quizAnswer);
    }

    public void delete(final Integer id) {
        quizAnswerRepository.deleteById(id);
    }

    private QuizAnswerDTO mapToDTO(final QuizAnswer quizAnswer, final QuizAnswerDTO quizAnswerDTO) {
        quizAnswerDTO.setId(quizAnswer.getId());
        quizAnswerDTO.setAnswer(quizAnswer.getAnswer());
        quizAnswerDTO.setScore(quizAnswer.getScore());
        quizAnswerDTO.setQuizQuestions(quizAnswer.getQuizQuestions() == null ? null : quizAnswer.getQuizQuestions().getId());
        quizAnswerDTO.setUsers(quizAnswer.getUsers() == null ? null : quizAnswer.getUsers().getId());
        return quizAnswerDTO;
    }

    private QuizAnswer mapToEntity(final QuizAnswerDTO quizAnswerDTO, final QuizAnswer quizAnswer) {
        quizAnswer.setAnswer(quizAnswerDTO.getAnswer());
        quizAnswer.setScore(quizAnswerDTO.getScore());
        final QuizQuestion quizQuestions = quizAnswerDTO.getQuizQuestions() == null ? null : quizQuestionRepository.findById(quizAnswerDTO.getQuizQuestions())
                .orElseThrow(() -> new NotFoundException("quizQuestions not found"));
        quizAnswer.setQuizQuestions(quizQuestions);
        final User users = quizAnswerDTO.getUsers() == null ? null : userRepository.findById(quizAnswerDTO.getUsers())
                .orElseThrow(() -> new NotFoundException("users not found"));
        quizAnswer.setUsers(users);
        return quizAnswer;
    }

}
