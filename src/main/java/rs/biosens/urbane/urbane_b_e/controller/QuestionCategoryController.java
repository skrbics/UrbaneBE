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
import rs.biosens.urbane.urbane_b_e.model.QuestionCategoryDTO;
import rs.biosens.urbane.urbane_b_e.service.QuestionCategoryService;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/questionCategories")
public class QuestionCategoryController {

    private final QuestionCategoryService questionCategoryService;

    public QuestionCategoryController(final QuestionCategoryService questionCategoryService) {
        this.questionCategoryService = questionCategoryService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("questionCategories", questionCategoryService.findAll());
        return "questionCategory/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("questionCategory") final QuestionCategoryDTO questionCategoryDTO) {
        return "questionCategory/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("questionCategory") @Valid final QuestionCategoryDTO questionCategoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "questionCategory/add";
        }
        questionCategoryService.create(questionCategoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("questionCategory.create.success"));
        return "redirect:/questionCategories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("questionCategory", questionCategoryService.get(id));
        return "questionCategory/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("questionCategory") @Valid final QuestionCategoryDTO questionCategoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "questionCategory/edit";
        }
        questionCategoryService.update(id, questionCategoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("questionCategory.update.success"));
        return "redirect:/questionCategories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = questionCategoryService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            questionCategoryService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("questionCategory.delete.success"));
        }
        return "redirect:/questionCategories";
    }

}
