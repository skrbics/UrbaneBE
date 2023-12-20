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
import rs.biosens.urbane.urbane_b_e.domain.User;
import rs.biosens.urbane.urbane_b_e.model.QuizResultDTO;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.service.QuizResultService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/quizResults")
public class QuizResultController {

    private final QuizResultService quizResultService;
    private final UserRepository userRepository;

    public QuizResultController(final QuizResultService quizResultService,
            final UserRepository userRepository) {
        this.quizResultService = quizResultService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("usersValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getPhone)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("quizResults", quizResultService.findAll());
        return "quizResult/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("quizResult") final QuizResultDTO quizResultDTO) {
        return "quizResult/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("quizResult") @Valid final QuizResultDTO quizResultDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizResult/add";
        }
        quizResultService.create(quizResultDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizResult.create.success"));
        return "redirect:/quizResults";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("quizResult", quizResultService.get(id));
        return "quizResult/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("quizResult") @Valid final QuizResultDTO quizResultDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizResult/edit";
        }
        quizResultService.update(id, quizResultDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizResult.update.success"));
        return "redirect:/quizResults";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        quizResultService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("quizResult.delete.success"));
        return "redirect:/quizResults";
    }

}
