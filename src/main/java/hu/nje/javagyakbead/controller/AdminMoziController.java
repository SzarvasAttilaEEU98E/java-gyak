
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.service.MoziService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/mozik")
@PreAuthorize("hasRole('ADMIN')")
public class AdminMoziController {

    private final MoziService moziService;

    public AdminMoziController(MoziService moziService) {
        this.moziService = moziService;
    }

    // Mozik listázása
    @GetMapping
    public String listMozik(Model model) {
        model.addAttribute("mozik", moziService.getAllMozik());
        return "admin-mozik";
    }

    // Új mozi űrlapjának megjelenítése
    @GetMapping("/uj")
    public String newMoziForm(Model model) {
        model.addAttribute("mozi", new Mozi());
        return "admin-mozi-form";
    }

    // Meglévő mozi szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editMoziForm(
            @PathVariable Long id,
            Model model) {

        Mozi mozi = moziService.getMoziById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nem található mozi ezzel az ID-val: " + id
                        )
                );

        model.addAttribute("mozi", mozi);
        return "admin-mozi-form";
    }

    // Mozi mentése
    @PostMapping("/mentes")
    public String saveMozi(@ModelAttribute Mozi mozi) {
        moziService.saveMozi(mozi);
        return "redirect:/admin/mozik";
    }

    // Mozi törlése
    @PostMapping("/torles/{id}")
    public String deleteMozi(@PathVariable Long id) {
        moziService.deleteMozi(id);
        return "redirect:/admin/mozik";
    }
}
