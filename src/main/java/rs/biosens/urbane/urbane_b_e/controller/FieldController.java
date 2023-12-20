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
import rs.biosens.urbane.urbane_b_e.model.FieldDTO;
import rs.biosens.urbane.urbane_b_e.repos.UserRepository;
import rs.biosens.urbane.urbane_b_e.service.FieldService;
import rs.biosens.urbane.urbane_b_e.util.CustomCollectors;
import rs.biosens.urbane.urbane_b_e.util.WebUtils;


@Controller
@RequestMapping("/fields")
public class FieldController {

    private final FieldService fieldService;
    private final UserRepository userRepository;

    public FieldController(final FieldService fieldService, final UserRepository userRepository) {
        this.fieldService = fieldService;
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
        model.addAttribute("fields", fieldService.findAll());
        return "field/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("field") final FieldDTO fieldDTO) {
        return "field/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("field") @Valid final FieldDTO fieldDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "field/add";
        }
        fieldService.create(fieldDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("field.create.success"));
        return "redirect:/fields";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id, final Model model) {
        model.addAttribute("field", fieldService.get(id));
        return "field/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Integer id,
            @ModelAttribute("field") @Valid final FieldDTO fieldDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "field/edit";
        }
        fieldService.update(id, fieldDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("field.update.success"));
        return "redirect:/fields";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Integer id,
            final RedirectAttributes redirectAttributes) {
        fieldService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("field.delete.success"));
        return "redirect:/fields";
    }

}
