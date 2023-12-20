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
import rs.biosens.urbane.urbane_b_e.domain.QuizQuestion;
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.QuizAnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuizQuestionRepository;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.service.QuizAnswerService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/quizAnswers")
public class QuizAnswerController {

    private final QuizAnswerService quizAnswerService;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;

    public QuizAnswerController(final QuizAnswerService quizAnswerService,
            final QuizQuestionRepository quizQuestionRepository,
            final UserRepository userRepository) {
        this.quizAnswerService = quizAnswerService;
        this.quizQuestionRepository = quizQuestionRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("quizQuestionsValues", quizQuestionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(QuizQuestion::getId, QuizQuestion::getId)));
        model.addAttribute("usersValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getPhone)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("quizAnswers", quizAnswerService.findAll());
        return "quizAnswer/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("quizAnswer") final QuizAnswerDTO quizAnswerDTO) {
        return "quizAnswer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("quizAnswer") @Valid final QuizAnswerDTO quizAnswerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizAnswer/add";
        }
        quizAnswerService.create(quizAnswerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizAnswer.create.success"));
        return "redirect:/quizAnswers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("quizAnswer", quizAnswerService.get(id));
        return "quizAnswer/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("quizAnswer") @Valid final QuizAnswerDTO quizAnswerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizAnswer/edit";
        }
        quizAnswerService.update(id, quizAnswerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizAnswer.update.success"));
        return "redirect:/quizAnswers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        quizAnswerService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("quizAnswer.delete.success"));
        return "redirect:/quizAnswers";
    }

}
