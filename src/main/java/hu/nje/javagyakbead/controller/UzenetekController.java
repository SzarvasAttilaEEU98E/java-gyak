
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.service.UzenetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    private final UzenetService uzenetService;

    public UzenetekController(UzenetService uzenetService) {
        this.uzenetService = uzenetService;
    }

    // Beküldött üzenetek megjelenítése
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {

        // Üzenetek lekérdezése fordított időrendben
        model.addAttribute(
                "uzenetek",
                uzenetService.getAllUzenetek()
        );

        return "uzenetek";
    }
}
