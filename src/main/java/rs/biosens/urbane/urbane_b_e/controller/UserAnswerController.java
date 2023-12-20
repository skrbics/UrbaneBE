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
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.UserAnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.AnswerRepository;
import rs.biosens.urbane.urbane_b_e.repos.SolutionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.service.UserAnswerService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/userAnswers")
public class UserAnswerController {

    private final UserAnswerService userAnswerService;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final SolutionRepository solutionRepository;

    public UserAnswerController(final UserAnswerService userAnswerService,
            final UserRepository userRepository, final AnswerRepository answerRepository,
            final SolutionRepository solutionRepository) {
        this.userAnswerService = userAnswerService;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.solutionRepository = solutionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usersValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getPhone)));
        model.addAttribute("answersValues", answerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Answer::getId, Answer::getId)));
        model.addAttribute("solutionsValues", solutionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Solution::getId, Solution::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userAnswers", userAnswerService.findAll());
        return "userAnswer/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("userAnswer") final UserAnswerDTO userAnswerDTO) {
        return "userAnswer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("userAnswer") @Valid final UserAnswerDTO userAnswerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userAnswer/add";
        }
        userAnswerService.create(userAnswerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userAnswer.create.success"));
        return "redirect:/userAnswers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("userAnswer", userAnswerService.get(id));
        return "userAnswer/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("userAnswer") @Valid final UserAnswerDTO userAnswerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userAnswer/edit";
        }
        userAnswerService.update(id, userAnswerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userAnswer.update.success"));
        return "redirect:/userAnswers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        userAnswerService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("userAnswer.delete.success"));
        return "redirect:/userAnswers";
    }

}
