package rs.biosens.urbane.urbane_b_e.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.biosens.urbane.urbane_b_e.domain.Answer;
import rs.biosens.urbane.urbane_b_e.domain.Solution;
import rs.biosens.urbane.urbane_b_e.model.MatrixDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.SolutionRepository;
import rs.biosens.urbane.urbane_b_e.service.MatrixService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/matrices")
public class MatrixController {

    private final MatrixService matrixService;
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    public MatrixController(final MatrixService matrixService,
            final AnswerRepository answerRepository, final SolutionRepository solutionRepository) {
        this.matrixService = matrixService;
        this.answerRepository = answerRepository;
        this.solutionRepository = solutionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("answersValues", answerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Answer::getId, Answer::getId)));
        model.addAttribute("solutionsValues", solutionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Solution::getId, Solution::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("matrixes", matrixService.findAll());
        return "matrix/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("matrix") final MatrixDTO matrixDTO) {
        return "matrix/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("matrix") @Valid final MatrixDTO matrixDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "matrix/add";
        }
        matrixService.create(matrixDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("matrix.create.success"));
        return "redirect:/matrices";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("matrix", matrixService.get(id));
        return "matrix/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("matrix") @Valid final MatrixDTO matrixDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "matrix/edit";
        }
        matrixService.update(id, matrixDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("matrix.update.success"));
        return "redirect:/matrices";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        matrixService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("matrix.delete.success"));
        return "redirect:/matrices";
    }

}
