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
import rs.biosens.urbane.urbane_b_e.domain.QuestionCategory;
import rs.biosens.urbane.urbane_b_e.model.QuestionDTO;
import rs.biosens.urbane.urbane_b_e.repos.QuestionCategoryRepository;
import rs.biosens.urbane.urbane_b_e.service.QuestionService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionCategoryRepository questionCategoryRepository;

    public QuestionController(final QuestionService questionService,
            final QuestionCategoryRepository questionCategoryRepository) {
        this.questionService = questionService;
        this.questionCategoryRepository = questionCategoryRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("questionCategoriesValues", questionCategoryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(QuestionCategory::getId, QuestionCategory::getCategory)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "question/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("question") final QuestionDTO questionDTO) {
        return "question/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("question") @Valid final QuestionDTO questionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "question/add";
        }
        questionService.create(questionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("question.create.success"));
        return "redirect:/questions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("question", questionService.get(id));
        return "question/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("question") @Valid final QuestionDTO questionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "question/edit";
        }
        questionService.update(id, questionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("question.update.success"));
        return "redirect:/questions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = questionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            questionService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("question.delete.success"));
        }
        return "redirect:/questions";
    }

}
