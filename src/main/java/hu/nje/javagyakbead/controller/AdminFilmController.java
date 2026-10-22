
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.request.FilmRequest;
import hu.nje.javagyakbead.service.FilmService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/filmek")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFilmController {

    private final FilmService filmService;

    public AdminFilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Filmek listázása
    @GetMapping
    public String listFilms(Model model) {

        model.addAttribute("filmek", filmService.getAllFilms());

        return "admin-filmek";
    }

    // Új film űrlapja
    @GetMapping("/uj")
    public String newFilmForm(Model model) {

        model.addAttribute("film", new FilmRequest());

        return "admin-film-form";
    }

    // Meglévő film szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editFilmForm(
            @PathVariable Long id,
            Model model) {

        Film film = filmService.getFilmById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nem található film ezzel az ID-val: " + id
                ));

        FilmRequest request = new FilmRequest();

        request.setId(film.getId());
        request.setCim(film.getCim());
        request.setEv(film.getEv());
        request.setHossz(film.getHossz());

        model.addAttribute("film", request);

        return "admin-film-form";
    }

    // Film mentése
    @PostMapping("/mentes")
    public String saveFilm(
            @Valid @ModelAttribute("film") FilmRequest request,
            BindingResult bindingResult,
            Model model) {

        // Request validációs hibák kezelése
        if (bindingResult.hasErrors()) {
            return "admin-film-form";
        }

        try {

            filmService.saveFilm(request);

            return "redirect:/admin/filmek";

        } catch (IllegalArgumentException exception) {

            model.addAttribute("error", exception.getMessage());

            return "admin-film-form";
        }
    }

    // Film biztonságos törlése és hibakezelés
    @PostMapping("/torles/{id}")
    public String deleteFilm(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            // Film törlésének megkísérlése
            filmService.deleteFilm(id);

            // Sikeres törlés visszajelzése
            redirectAttributes.addFlashAttribute(
                    "success",
                    "A film sikeresen törölve."
            );

        } catch (IllegalArgumentException exception) {

            // Sikertelen törlés hibaüzenete
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );
        }

        return "redirect:/admin/filmek";
    }
}
