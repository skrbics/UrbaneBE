package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Matrix;
import rs.biosens.urbane.urbane_b_e.domain.Solution;
import rs.biosens.urbane.urbane_b_e.model.MatrixDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.MatrixRepository;
import rs.biosens.urbane.urbane_b_e.repos.SolutionRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;


@Service
public class MatrixService {

    private final MatrixRepository matrixRepository;
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    public MatrixService(final MatrixRepository matrixRepository,
            final AnswerRepository answerRepository, final SolutionRepository solutionRepository) {
        this.matrixRepository = matrixRepository;
        this.answerRepository = answerRepository;
        this.solutionRepository = solutionRepository;
    }

    public List<MatrixDTO> findAll() {
        final List<Matrix> matrixes = matrixRepository.findAll(Sort.by("id"));
        return matrixes.stream()
                .map(matrix -> mapToDTO(matrix, new MatrixDTO()))
                .toList();
    }

    public MatrixDTO get(final Integer id) {
        return matrixRepository.findById(id)
                .map(matrix -> mapToDTO(matrix, new MatrixDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MatrixDTO matrixDTO) {
        final Matrix matrix = new Matrix();
        mapToEntity(matrixDTO, matrix);
        return matrixRepository.save(matrix).getId();
    }

    public void update(final Integer id, final MatrixDTO matrixDTO) {
        final Matrix matrix = matrixRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(matrixDTO, matrix);
        matrixRepository.save(matrix);
    }

    public void delete(final Integer id) {
        matrixRepository.deleteById(id);
    }

    private MatrixDTO mapToDTO(final Matrix matrix, final MatrixDTO matrixDTO) {
        matrixDTO.setId(matrix.getId());
        matrixDTO.setScore(matrix.getScore());
        matrixDTO.setAnswers(matrix.getAnswers() == null ? null : matrix.getAnswers().getId());
        matrixDTO.setSolutions(matrix.getSolutions() == null ? null : matrix.getSolutions().getId());
        return matrixDTO;
    }

    private Matrix mapToEntity(final MatrixDTO matrixDTO, final Matrix matrix) {
        matrix.setScore(matrixDTO.getScore());
        final Answer answers = matrixDTO.getAnswers() == null ? null : answerRepository.findById(matrixDTO.getAnswers())
                .orElseThrow(() -> new NotFoundException("answers not found"));
        matrix.setAnswers(answers);
        final Solution solutions = matrixDTO.getSolutions() == null ? null : solutionRepository.findById(matrixDTO.getSolutions())
                .orElseThrow(() -> new NotFoundException("solutions not found"));
        matrix.setSolutions(solutions);
        return matrix;
    }

}
