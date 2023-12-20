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
import rs.biosens.urbane.urbane_b_e.domain.Question;
import rs.biosens.urbane.urbane_b_e.model.AnswerDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuestionRepository;
import rs.biosens.urbane.urbane_b_e.service.AnswerService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionRepository questionRepository;

    public AnswerController(final AnswerService answerService,
            final QuestionRepository questionRepository) {
        this.answerService = answerService;
        this.questionRepository = questionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("questionsValues", questionRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Question::getId, Question::getType)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("answers", answerService.findAll());
        return "answer/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("answer") final AnswerDTO answerDTO) {
        return "answer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("answer") @Valid final AnswerDTO answerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "answer/add";
        }
        answerService.create(answerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("answer.create.success"));
        return "redirect:/answers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("answer", answerService.get(id));
        return "answer/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("answer") @Valid final AnswerDTO answerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "answer/edit";
        }
        answerService.update(id, answerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("answer.update.success"));
        return "redirect:/answers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = answerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            answerService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("answer.delete.success"));
        }
        return "redirect:/answers";
    }

}
