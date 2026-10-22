
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.service.FilmService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Új film űrlapjának megjelenítése
    @GetMapping("/uj")
    public String newFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "admin-film-form";
    }

    // Meglévő film szerkesztése
    @GetMapping("/szerkesztes/{id}")
    public String editFilmForm(
            @PathVariable Long id,
            Model model) {

        Film film = filmService.getFilmById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Nem található film ezzel az ID-val: " + id
                        )
                );

        model.addAttribute("film", film);
        return "admin-film-form";
    }

    // Film mentése
    @PostMapping("/mentes")
    public String saveFilm(@ModelAttribute Film film) {
        filmService.saveFilm(film);
        return "redirect:/admin/filmek";
    }

    // Film törlése
    @PostMapping("/torles/{id}")
    public String deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return "redirect:/admin/filmek";
    }
}
