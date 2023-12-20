package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.QuizResult;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.QuizResultDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuizResultRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;


@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final UserRepository userRepository;

    public QuizResultService(final QuizResultRepository quizResultRepository,
            final UserRepository userRepository) {
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
    }

    public List<QuizResultDTO> findAll() {
        final List<QuizResult> quizResults = quizResultRepository.findAll(Sort.by("id"));
        return quizResults.stream()
                .map(quizResult -> mapToDTO(quizResult, new QuizResultDTO()))
                .toList();
    }

    public QuizResultDTO get(final Integer id) {
        return quizResultRepository.findById(id)
                .map(quizResult -> mapToDTO(quizResult, new QuizResultDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final QuizResultDTO quizResultDTO) {
        final QuizResult quizResult = new QuizResult();
        mapToEntity(quizResultDTO, quizResult);
        return quizResultRepository.save(quizResult).getId();
    }

    public void update(final Integer id, final QuizResultDTO quizResultDTO) {
        final QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(quizResultDTO, quizResult);
        quizResultRepository.save(quizResult);
    }

    public void delete(final Integer id) {
        quizResultRepository.deleteById(id);
    }

    private QuizResultDTO mapToDTO(final QuizResult quizResult, final QuizResultDTO quizResultDTO) {
        quizResultDTO.setId(quizResult.getId());
        quizResultDTO.setScore(quizResult.getScore());
        quizResultDTO.setCreated(quizResult.getCreated());
        quizResultDTO.setUsers(quizResult.getUsers() == null ? null : quizResult.getUsers().getId());
        return quizResultDTO;
    }

    private QuizResult mapToEntity(final QuizResultDTO quizResultDTO, final QuizResult quizResult) {
        quizResult.setScore(quizResultDTO.getScore());
        quizResult.setCreated(quizResultDTO.getCreated());
        final User users = quizResultDTO.getUsers() == null ? null : userRepository.findById(quizResultDTO.getUsers())
                .orElseThrow(() -> new NotFoundException("users not found"));
        quizResult.setUsers(users);
        return quizResult;
    }

}
