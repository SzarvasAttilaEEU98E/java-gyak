
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/filmek")
    public String getFilmek(Model model) {
        model.addAttribute("filmek", filmService.getAllFilms());

        return "filmek";
    }
}
