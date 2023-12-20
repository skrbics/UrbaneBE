package rs.biosens.urbane.urbane_b_e.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.biosens.urbane.urbane_b_e.model.QuizQuestionDTO;
import rs.biosens.urbane.urbane_b_e.service.QuizQuestionService;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/quizQuestions")
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(final QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("quizQuestions", quizQuestionService.findAll());
        return "quizQuestion/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("quizQuestion") final QuizQuestionDTO quizQuestionDTO) {
        return "quizQuestion/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("quizQuestion") @Valid final QuizQuestionDTO quizQuestionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizQuestion/add";
        }
        quizQuestionService.create(quizQuestionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizQuestion.create.success"));
        return "redirect:/quizQuestions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("quizQuestion", quizQuestionService.get(id));
        return "quizQuestion/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("quizQuestion") @Valid final QuizQuestionDTO quizQuestionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "quizQuestion/edit";
        }
        quizQuestionService.update(id, quizQuestionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("quizQuestion.update.success"));
        return "redirect:/quizQuestions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = quizQuestionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            quizQuestionService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("quizQuestion.delete.success"));
        }
        return "redirect:/quizQuestions";
    }

}
