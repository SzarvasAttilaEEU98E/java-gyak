
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.service.EloadasService;
import hu.nje.javagyakbead.service.FilmService;
import hu.nje.javagyakbead.service.MoziService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        model.addAttribute("eloadas", new Eloadas());
        addFormData(model);

        return "admin-eloadas-form";
    }

    // Meglévő előadás szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editEloadasForm(
            @PathVariable Long id,
            Model model) {

        Eloadas eloadas = eloadasService.getEloadasById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nem található előadás ezzel az ID-val: " + id
                        )
                );

        model.addAttribute("eloadas", eloadas);
        addFormData(model);

        return "admin-eloadas-form";
    }

    // Előadás mentése
    @PostMapping("/mentes")
    public String saveEloadas(
            @ModelAttribute Eloadas eloadas,
            Model model) {

        if (eloadas.getFilmId() == null ||
                filmService.getFilmById(eloadas.getFilmId()).isEmpty()) {

            model.addAttribute("error", "A kiválasztott film nem létezik.");
            model.addAttribute("eloadas", eloadas);
            addFormData(model);

            return "admin-eloadas-form";
        }

        if (eloadas.getMoziId() == null ||
                moziService.getMoziById(eloadas.getMoziId()).isEmpty()) {

            model.addAttribute("error", "A kiválasztott mozi nem létezik.");
            model.addAttribute("eloadas", eloadas);
            addFormData(model);

            return "admin-eloadas-form";
        }

        if (eloadas.getDatum() == null ||
                eloadas.getNezoszam() == null ||
                eloadas.getNezoszam() < 0 ||
                eloadas.getBevetel() == null ||
                eloadas.getBevetel() < 0) {

            model.addAttribute("error", "Érvénytelen előadásadatok.");
            model.addAttribute("eloadas", eloadas);
            addFormData(model);

            return "admin-eloadas-form";
        }

        // Meglévő előadás módosításakor ellenőrizzük az ID-t
        if (eloadas.getId() != null &&
                eloadasService.getEloadasById(eloadas.getId()).isEmpty()) {

            model.addAttribute("error", "A szerkesztett előadás nem található.");
            model.addAttribute("eloadas", eloadas);
            addFormData(model);

            return "admin-eloadas-form";
        }

        eloadasService.saveEloadas(eloadas);

        return "redirect:/admin/eloadasok";
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
