
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.request.EloadasRequest;
import hu.nje.javagyakbead.service.EloadasService;
import hu.nje.javagyakbead.service.FilmService;
import hu.nje.javagyakbead.service.MoziService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/eloadasok")
@PreAuthorize("hasRole('ADMIN')")
public class AdminEloadasController {

    private final EloadasService eloadasService;
    private final FilmService filmService;
    private final MoziService moziService;

    public AdminEloadasController(
            EloadasService eloadasService,
            FilmService filmService,
            MoziService moziService) {

        this.eloadasService = eloadasService;
        this.filmService = filmService;
        this.moziService = moziService;
    }

    // Előadások lapozható listázása
    @GetMapping
    public String listEloadasok(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 20;
        page = Math.max(page, 0);

        Page<Eloadas> eloadasok =
                eloadasService.getAllEloadasok(page, pageSize);

        model.addAttribute("eloadasok", eloadasok.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eloadasok.getTotalPages());
        model.addAttribute("totalElements", eloadasok.getTotalElements());

        return "admin-eloadasok";
    }

    // Új előadás űrlapja
    @GetMapping("/uj")
    public String newEloadasForm(Model model) {

        model.addAttribute("eloadas", new EloadasRequest());

        addFormData(model);

        return "admin-eloadas-form";
    }

    // Meglévő előadás szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editEloadasForm(
            @PathVariable Long id,
            Model model) {

        Eloadas eloadas = eloadasService.getEloadasById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nem található előadás ezzel az ID-val: " + id
                ));

        EloadasRequest request = new EloadasRequest();

        request.setId(eloadas.getId());
        request.setFilmId(eloadas.getFilmId());
        request.setMoziId(eloadas.getMoziId());
        request.setDatum(eloadas.getDatum());
        request.setNezoszam(eloadas.getNezoszam());
        request.setBevetel(eloadas.getBevetel());

        model.addAttribute("eloadas", request);

        addFormData(model);

        return "admin-eloadas-form";
    }

    // Előadás mentése
    @PostMapping("/mentes")
    public String saveEloadas(
            @Valid @ModelAttribute("eloadas") EloadasRequest request,
            BindingResult bindingResult,
            Model model) {

        // Request validációs hibák kezelése
        if (bindingResult.hasErrors()) {

            addFormData(model);

            return "admin-eloadas-form";
        }

        try {

            // Service és üzleti validáció
            eloadasService.saveEloadas(request);

            return "redirect:/admin/eloadasok";

        } catch (IllegalArgumentException exception) {

            // Üzleti validációs hiba megjelenítése
            model.addAttribute("error", exception.getMessage());

            addFormData(model);

            return "admin-eloadas-form";
        }
    }

    // Előadás törlése
    @PostMapping("/torles/{id}")
    public String deleteEloadas(@PathVariable Long id) {

        eloadasService.deleteEloadas(id);

        return "redirect:/admin/eloadasok";
    }

    // Filmek és mozik betöltése a legördülő listákhoz
    private void addFormData(Model model) {

        model.addAttribute("filmek", filmService.getAllFilms());
        model.addAttribute("mozik", moziService.getAllMozik());
    }
}
