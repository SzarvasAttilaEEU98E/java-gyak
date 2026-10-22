
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.request.UzenetRequest;
import hu.nje.javagyakbead.service.UzenetService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kapcsolat")
public class KapcsolatController {

    private final UzenetService uzenetService;

    public KapcsolatController(UzenetService uzenetService) {
        this.uzenetService = uzenetService;
    }

    // Kapcsolatfelvételi űrlap megjelenítése
    @GetMapping
    public String kapcsolatForm(Model model) {

        model.addAttribute("uzenet", new UzenetRequest());

        return "kapcsolat";
    }

    // Kapcsolatfelvételi űrlap feldolgozása
    @PostMapping
    public String kuldes(
            @Valid @ModelAttribute("uzenet") UzenetRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        // Szerveroldali validációs hibák kezelése
        if (bindingResult.hasErrors()) {
            return "kapcsolat";
        }

        // Üzenet mentése az adatbázisba
        uzenetService.saveUzenet(request);

        // Sikeres küldés visszajelzése
        redirectAttributes.addFlashAttribute(
                "success",
                "Az üzenetedet sikeresen elküldtük."
        );

        return "redirect:/kapcsolat";
    }
}
