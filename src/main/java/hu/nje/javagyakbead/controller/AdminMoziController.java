
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.request.MoziRequest;
import hu.nje.javagyakbead.service.MoziService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    // Új mozi űrlapja
    @GetMapping("/uj")
    public String newMoziForm(Model model) {

        model.addAttribute("mozi", new MoziRequest());

        return "admin-mozi-form";
    }

    // Meglévő mozi szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editMoziForm(
            @PathVariable Long id,
            Model model) {

        Mozi mozi = moziService.getMoziById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nem található mozi ezzel az ID-val: " + id
                ));

        MoziRequest request = new MoziRequest();

        request.setId(mozi.getId());
        request.setNev(mozi.getNev());
        request.setVaros(mozi.getVaros());
        request.setFerohely(mozi.getFerohely());

        model.addAttribute("mozi", request);

        return "admin-mozi-form";
    }

    // Mozi mentése
    @PostMapping("/mentes")
    public String saveMozi(
            @Valid @ModelAttribute("mozi") MoziRequest request,
            BindingResult bindingResult,
            Model model) {

        // Request validációs hibák kezelése
        if (bindingResult.hasErrors()) {

            return "admin-mozi-form";
        }

        try {

            // Service meghívása, üzleti validáció
            moziService.saveMozi(request);

            return "redirect:/admin/mozik";

        } catch (IllegalArgumentException exception) {

            // Üzleti validációs hiba megjelenítése
            model.addAttribute("error", exception.getMessage());

            return "admin-mozi-form";
        }
    }

    // Mozi törlése
    @PostMapping("/torles/{id}")
    public String deleteMozi(@PathVariable Long id) {

        moziService.deleteMozi(id);

        return "redirect:/admin/mozik";
    }
}
