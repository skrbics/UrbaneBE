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
import rs.biosens.urbane.urbane_b_e.model.SolutionDTO;
import rs.biosens.urbane.urbane_b_e.service.SolutionService;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    public SolutionController(final SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("solutions", solutionService.findAll());
        return "solution/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("solution") final SolutionDTO solutionDTO) {
        return "solution/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("solution") @Valid final SolutionDTO solutionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "solution/add";
        }
        solutionService.create(solutionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("solution.create.success"));
        return "redirect:/solutions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("solution", solutionService.get(id));
        return "solution/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("solution") @Valid final SolutionDTO solutionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "solution/edit";
        }
        solutionService.update(id, solutionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("solution.update.success"));
        return "redirect:/solutions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = solutionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            solutionService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("solution.delete.success"));
        }
        return "redirect:/solutions";
    }

}
