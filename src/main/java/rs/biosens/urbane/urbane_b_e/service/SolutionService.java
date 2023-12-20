package rs.biosens.urbane.urbane_b_e.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.biosens.urbane.urbane_b_e.domain.Matrix;
import rs.biosens.urbane.urbane_b_e.domain.Solution;
import rs.biosens.urbane.urbane_b_e.domain.UserAnswer;
import rs.biosens.urbane.urbane_b_e.model.SolutionDTO;
import rs.biosens.urbane.urbane_b_e.repos.MatrixRepository;
import rs.biosens.urbane.urbane_b_e.repos.SolutionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserAnswerRepository;
import rs.biosens.urbane.urbane_b_e.util.NotFoundException;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final MatrixRepository matrixRepository;
    private final UserAnswerRepository userAnswerRepository;

    public SolutionService(final SolutionRepository solutionRepository,
            final MatrixRepository matrixRepository,
            final UserAnswerRepository userAnswerRepository) {
        this.solutionRepository = solutionRepository;
        this.matrixRepository = matrixRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    public List<SolutionDTO> findAll() {
        final List<Solution> solutions = solutionRepository.findAll(Sort.by("id"));
        return solutions.stream()
                .map(solution -> mapToDTO(solution, new SolutionDTO()))
                .toList();
    }

    public SolutionDTO get(final Integer id) {
        return solutionRepository.findById(id)
                .map(solution -> mapToDTO(solution, new SolutionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final SolutionDTO solutionDTO) {
        final Solution solution = new Solution();
        mapToEntity(solutionDTO, solution);
        return solutionRepository.save(solution).getId();
    }

    public void update(final Integer id, final SolutionDTO solutionDTO) {
        final Solution solution = solutionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(solutionDTO, solution);
        solutionRepository.save(solution);
    }

    public void delete(final Integer id) {
        solutionRepository.deleteById(id);
    }

    private SolutionDTO mapToDTO(final Solution solution, final SolutionDTO solutionDTO) {
        solutionDTO.setId(solution.getId());
        solutionDTO.setSolution(solution.getSolution());
        return solutionDTO;
    }

    private Solution mapToEntity(final SolutionDTO solutionDTO, final Solution solution) {
        solution.setSolution(solutionDTO.getSolution());
        return solution;
    }

    public String getReferencedWarning(final Integer id) {
        final Solution solution = solutionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Matrix solutionsMatrix = matrixRepository.findFirstBySolutions(solution);
        if (solutionsMatrix != null) {
            return WebUtils.getMessage("solution.matrix.solutions.referenced", solutionsMatrix.getId());
        }
        final UserAnswer solutionsUserAnswer = userAnswerRepository.findFirstBySolutions(solution);
        if (solutionsUserAnswer != null) {
            return WebUtils.getMessage("solution.userAnswer.solutions.referenced", solutionsUserAnswer.getId());
        }
        return null;
    }

}
